package lk.gov.govtech.covid19.controller;

import lk.gov.govtech.covid19.dto.AlertNotificationResponse;
import lk.gov.govtech.covid19.dto.CaseNotificationResponse;
import lk.gov.govtech.covid19.dto.StatusResponse;
import lk.gov.govtech.covid19.dto.UpdateStatusRequest;
import lk.gov.govtech.covid19.service.ApplicationService;
import lk.gov.govtech.covid19.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for all task force application related apis
 */
@Slf4j
@RestController
@RequestMapping(value = Constants.APPLICATION_API_CONTEXT)
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping(path = "/alert/{messageId}/{lang}", produces = "application/json")
    public ResponseEntity getAlert(@PathVariable("messageId") String messageId, @PathVariable("lang") String lang) {

        AlertNotificationResponse response = applicationService.getAlertNotification(messageId, lang);

        if (response == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(response);
        }

    }

    @GetMapping(path = "/alert/latest", produces = "application/json")
    public ResponseEntity getLastAlertId() {

        return ResponseEntity.ok().body(applicationService.getLastAlertNotificationId());

    }

    @GetMapping(path = "/case/{caseId}/{lang}", produces = "application/json")
    public ResponseEntity getCase(@PathVariable("caseId") String caseId, @PathVariable("lang") String lang) {
        CaseNotificationResponse response = applicationService.getCaseNotification(caseId, lang);

        if (response == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(response);
        }

    }

    @GetMapping(path = "/case/latest")
    public ResponseEntity getLastCaseId(){
        return ResponseEntity.ok().body(applicationService.getLastCaseNotificationId());
    }

    //get Covid-19 Status
    @GetMapping(path = "/dashboard/status", produces = "application/json")
    public ResponseEntity getSatus() {
        StatusResponse response = applicationService.getStatus();

        if (response == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok().body(response);
        }

    }

    //Update Covid-19 Status
    @PutMapping(path = "/dashboard/status", consumes = "application/json", produces = "application/json")
    public ResponseEntity updateStatus(@RequestBody UpdateStatusRequest request){

        if(request==null){
            return ResponseEntity.noContent().build();
        }else {
            applicationService.updateStatus(request);
            return ResponseEntity.accepted().build();
        }

    }
}
