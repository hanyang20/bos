package com.itheima.bos.service.take_delivery;

import com.itheima.bos.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionService {
    void save(Promotion promotion);

    Page<Promotion> pageQuery(Pageable pageable);
}
