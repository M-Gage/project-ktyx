package com.ywkj.ktyunxiao.service.thread;

import com.ywkj.ktyunxiao.dao.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

/**
 * @author MiaoGuoZhu
 * @date 2018/7/9 13:37
 */
@Service
public class OrderThread implements Callable<Integer> {


    private CustomerMapper customerMapper;

    private String companyId;
    private String customerId;

    @Override
    public Integer call() throws Exception {
        return customerMapper.updateLastOrderTime(this.customerId,this.companyId);
    }

    public OrderThread() {
    }

    public OrderThread(String companyId, String customerId,CustomerMapper customerMapper) {
        this.companyId = companyId;
        this.customerId = customerId;
        this.customerMapper = customerMapper;
    }
}
