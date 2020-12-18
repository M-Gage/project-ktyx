package com.ywkj.ktyunxiao.dao;

import com.ywkj.ktyunxiao.model.Contact;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiWenHao
 * @date 2018/6/7 16:10
 */
@Component
public interface ContactMapper {

    /**
     * 批量添加联系人
     * @param contactList
     * @return
     */
    int insertList(@Param("contactList") List<Contact> contactList);

    /**
     * 批量修改联系人
     * @param contactList
     * @return
     */
    int updateList(@Param("contactList") List<Contact> contactList);

    /**
     * 删除联系人根据联系人Id
     * @param contactIdArray
     * @param customerId
     * @return
     */
    int deleteListByPrimaryId(@Param("contactIdArray") String[] contactIdArray, @Param("customerId") String customerId, @Param("companyId") String companyId);

    /**
     * 添加联系人
     * @param contact
     * @return
     */
    int insert(Contact contact);

}
