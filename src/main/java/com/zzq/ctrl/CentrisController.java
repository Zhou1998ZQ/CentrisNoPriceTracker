package com.zzq.ctrl;

import com.zzq.entity.Centris;
import com.zzq.service.CentrisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CentrisController {

    private final CentrisService centrisService;

    @PostMapping("/insertCentrisNo/{centrisNo}")
    public ResponseEntity<String> insertCentrisNo(@PathVariable Long centrisNo) {

        String result = centrisService.insertCentrisNo(centrisNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getCentrisNoData/{centrisNo}")
    public ResponseEntity<List<Centris>> getCentrisNoData(@PathVariable Long centrisNo) {
        List<Centris> centrisList = centrisService.getCentrisNoData(centrisNo);
        return ResponseEntity.ok(centrisList);
    }

    @PutMapping("/updateCentrisNoActive/{centrisNo}")
    public ResponseEntity<Void> updateCentrisNoActive(@PathVariable Long centrisNo,@RequestParam Boolean isActice) {
        centrisService.updateCentrisNoActive(centrisNo,isActice);
        return ResponseEntity.ok(null);
    }
}
