package com.zzq.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzq.entity.Centris;
import com.zzq.mapper.CentrisMapper;
import com.zzq.service.CentrisService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CentrisServiceImpl implements CentrisService {

    private final CentrisMapper centrisMapper;

    @Override
    public String insertCentrisNo(Long centrisNo) {
        String result;

        // verify the centrisNo
        String url = "https://www.centris.ca/fr/maison~a-vendre~gatineau-hull/" + centrisNo;
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
            String price = document.selectFirst("span[class=text-nowrap]").text();
            if (price.isBlank())
                throw new RuntimeException();
        } catch (Exception e) {
            e.printStackTrace();
            return StrUtil.format("centrisNo : {} error number", centrisNo);
        }


        List<Centris> centrises = centrisMapper.selectList(new LambdaQueryWrapper<Centris>()
                .eq(Centris::getCentrisNo, centrisNo)
        );
        if (centrises.isEmpty()) {
            centrisMapper.insert(new Centris().setIsActive(Boolean.TRUE).setCentrisNo(centrisNo));
            result = "Insert successful";
        } else {
            result = "The system already has this piece of data";
        }

        return result;
    }

    @Override
    public List<Centris> getCentrisNoData(Long centrisNo) {
        List<Centris> centrises = centrisMapper.selectList(new LambdaQueryWrapper<Centris>()
                .eq(Centris::getCentrisNo, centrisNo)
        );

        return centrises.stream().sorted(Comparator.comparing(Centris::getCreatedAt).reversed()).collect(Collectors.toList());
    }

    @Override
    public void updateCentrisNoActive(Long centrisNo, Boolean isActice) {
        // normally centrises size == 1
        List<Centris> centrises = centrisMapper.selectList(new LambdaQueryWrapper<Centris>()
                .eq(Centris::getCentrisNo, centrisNo)
        );


        if (centrises.isEmpty())
            return;

        for (Centris centris : centrises) {
            centrisMapper.updateById(centris.setIsActive(isActice));
        }


    }
}
