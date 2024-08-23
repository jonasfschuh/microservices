package io.github.kenzor1979.mscreditassessor.application;

import io.github.kenzor1979.mscreditassessor.application.ex.CustomerDataNotFoundException;
import io.github.kenzor1979.mscreditassessor.application.ex.MicroserviceCommunicationErrorException;
import io.github.kenzor1979.mscreditassessor.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("credit-assessor")
@RequiredArgsConstructor
public class CreditAssessorController {

    private final CreditAssessorService creditAssessorService;

    @GetMapping
    public String status() {
        return "credit-assessor ok";
    }

    @GetMapping(value = "customer-status", params = "cpf")
    public ResponseEntity checkCustomerStatus(@RequestParam("cpf") String cpf) {
        try {
            CustomerStatus customerStatus = creditAssessorService.getCustomerStatus(cpf);
            return ResponseEntity.ok(customerStatus);
        } catch (CustomerDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (MicroserviceCommunicationErrorException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity performAssessment(@RequestBody AssessmentData data) {
        try {
            CustomerReviewReturn customerReviewReturn = creditAssessorService
                    .performEvaluation(data.getCpf(), data.getRenda());
            return ResponseEntity.ok(customerReviewReturn);
        } catch (CustomerDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (MicroserviceCommunicationErrorException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
        try{
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = creditAssessorService
                    .solicitarEmissaoCartao(dados);
            return ResponseEntity.ok(protocoloSolicitacaoCartao);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
