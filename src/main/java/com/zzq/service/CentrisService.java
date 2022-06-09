package com.zzq.service;

import com.zzq.entity.Centris;

import java.util.List;

public interface CentrisService {

    String insertCentrisNo(Long centrisNo);

    List<Centris> getCentrisNoData(Long centrisNo);

    void updateCentrisNoActive(Long centrisNo,Boolean isActice);
}
