package io.github.kenzor1979.mscards.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kenzor1979.mscards.domain.Card;
import io.github.kenzor1979.mscards.domain.ClienteCard;
import io.github.kenzor1979.mscards.domain.DadosSolicitacaoEmissaoCartao;
import io.github.kenzor1979.mscards.infra.repository.CardRepository;
import io.github.kenzor1979.mscards.infra.repository.ClienteCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CardRepository cardRepository;
    private final ClienteCardRepository clienteCardRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {
        System.out.println(payload); //eliminar
        try {
            var mapper = new ObjectMapper();

            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Card card = cardRepository.findById(dados.getIdCartao()).orElseThrow();

            ClienteCard clienteCard = new ClienteCard();
            clienteCard.setCard(card);
            clienteCard.setCpf(dados.getCpf());
            clienteCard.setLimite(dados.getLimiteLiberado());

            clienteCardRepository.save(clienteCard);
        } catch (Exception e) {
            log.error("Erro ao receber solicitação de emissão de cartão: {}", e.getMessage());
            //e.printStackTrace();
        }
    }
}
