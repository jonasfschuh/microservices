package io.github.kenzor1979.mscreditassessor.application;

import feign.FeignException;
import io.github.kenzor1979.mscreditassessor.application.ex.CustomerDataNotFoundException;
import io.github.kenzor1979.mscreditassessor.application.ex.ErroSolicitacaoCartaoException;
import io.github.kenzor1979.mscreditassessor.application.ex.MicroserviceCommunicationErrorException;
import io.github.kenzor1979.mscreditassessor.application.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import io.github.kenzor1979.mscreditassessor.domain.model.*;
import io.github.kenzor1979.mscreditassessor.infra.clients.CardsResourceClient;
import io.github.kenzor1979.mscreditassessor.infra.clients.CustomerResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreditAssessorService {

    private final CustomerResourceClient customerResourceClient;
    private final CardsResourceClient cardsResourceClient;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

    public CustomerStatus getCustomerStatus(String cpf)
            throws CustomerDataNotFoundException, MicroserviceCommunicationErrorException {
        // obter DadosCliente - MSCLIENTES
        // obter Cartoes do cliente - MSCARTOES
        try {
            ResponseEntity<CustomerData> customerDataResponse = customerResourceClient.clientData(cpf);
            ResponseEntity<List<CustomerCards>> cardsResponse = cardsResourceClient.getCartoesByCliente(cpf);

            return CustomerStatus
                    .builder()
                    .customer(customerDataResponse.getBody())
                    .cards(cardsResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new CustomerDataNotFoundException();
            }
            throw new MicroserviceCommunicationErrorException(e.getMessage(), status);
        }
    }

    public CustomerReviewReturn performEvaluation(String cpf, Long renda)
            throws CustomerDataNotFoundException, MicroserviceCommunicationErrorException {
        try {
            ResponseEntity<CustomerData> customerDataResponse = customerResourceClient.clientData(cpf);
            ResponseEntity<List<Card>> cardsResponse = cardsResourceClient.getCartoesRendaAteh(renda);

            List<Card> cards = cardsResponse.getBody();
            var ApprovedCardsList = cards.stream().map(card -> {
                CustomerData customerData = customerDataResponse.getBody();

                BigDecimal limiteBasico = card.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(customerData.getIdade());
                var fate = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal approvedLimit = fate.multiply(limiteBasico);

                ApprovedCard approvedCard = new ApprovedCard();
                approvedCard.setCard(card.getNome());
                approvedCard.setFlag(card.getBandeira());
                approvedCard.setApprovedLimit(approvedLimit);

                return approvedCard;
            }).collect(Collectors.toList());

            return new CustomerReviewReturn(ApprovedCardsList);

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new CustomerDataNotFoundException();
            }
            throw new MicroserviceCommunicationErrorException(e.getMessage(), status);
        }
    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
        try{
            emissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        } catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }


}
