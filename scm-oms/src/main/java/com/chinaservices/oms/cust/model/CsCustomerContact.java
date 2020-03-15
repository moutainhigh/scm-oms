package com.chinaservices.oms.cust.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.chinaservices.sdk.binding.annotation.Table;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
*类用途说明：客户联系人管理
*By：厦门同创空间信息技术有限公司 www.chinaservices.com.cn
*@author chenqiwang
*/
@Table(tableName = "cs_customer_contact", pkName = "id")
public class CsCustomerContact extends Model<CsCustomerContact>{
	private static final long serialVersionUID = 1L;
	public static final CsCustomerContact dao = new CsCustomerContact();
	 /**
     * 分页查询
     *
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<CsCustomerContact> getPageByCondition(Map<String, Object> params, int pageNo, int pageSize) {
        String selectSql = "select t.* ";
        StringBuilder extSql = new StringBuilder();
        extSql.append("from cs_customer_contact t where 1=1");
        
        String customerId=(String)params.get("customer_id");
        List<String> param=new ArrayList<String>();
//        if(StringUtils.isNotBlank(customerId)){
        	extSql.append(" and customer_id=?");
        	param.add(customerId);
//        }
        extSql.append(" order by t.modify_time desc");
        if(param.isEmpty()){
        	return paginate(pageNo, pageSize, selectSql, extSql.toString());

        }else{
        	return paginate(pageNo, pageSize, selectSql, extSql.toString(),param.toArray());
        }
    }
    
    /**
     * 客户联系人弹出框分页查询: 查询启认联系人信息（弹出框专用）
     * @param params
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page<CsCustomerContact> getPopPageByCondition(Map<String, Object> params, int pageNo, int pageSize) {
        String selectSql = "select c.id, c.contact_code, c.contact_name, c.province_code, c.province_name, "
        		+ "c.city_code, c.city_name, c.county_code, c.county_name, c.address, c.mobile, c.tel, "
        		+ "t.customer_code, t.customer_name, t.customer_type, t.office ";
        StringBuilder extSql = new StringBuilder();
        extSql.append(" from cs_customer_contact c left join cs_customer t ON c.customer_id = t.id where 1 = 1 ");
        String contact_code = (String) params.get("contact_code");
        String contact_name = (String) params.get("contact_name");
        String customer_code = (String) params.get("customer_code");
        List<String> param = new ArrayList<String>();
        if (StringUtils.isNotBlank(contact_code)) {
        	extSql.append(" and c.contact_code like ? ");
        	param.add("%" + contact_code + "%");
        }
        if (StringUtils.isNotBlank(contact_name)) {
        	extSql.append(" and c.contact_name like ? ");
        	param.add("%" + contact_name + "% ");
        }
        if (StringUtils.isNotBlank(customer_code)) {
        	extSql.append(" and c.customer_code =? ");
        	param.add(customer_code);
        }
        extSql.append(" order by c.modify_time desc");
        if (param.isEmpty()) {
        	return paginate(pageNo, pageSize, selectSql, extSql.toString());
        } else {
        	return paginate(pageNo, pageSize, selectSql, extSql.toString(), param.toArray());
        }
    }
    
    /**
     * 根据id集合删除数据
     *
     * @param ids
     * @return
     */
    public int deleteAllById(Object[] ids) {
        if (null == ids || ids.length == 0) {
            return 0;
        } else {
            StringBuilder sql = new StringBuilder("delete from cs_customer_contract where id in(");
            for (int i = 0; i < ids.length; i++) {
                sql.append("?");
                if (i < (ids.length - 1)) {
                    sql.append(",");
                }
            }
            sql.append(")");
            return Db.update(sql.toString(), ids);
        }
    }
    
    /**
     * 保存或者更新
     *
     * @param customer
     */
    public void saveOrUpdate(CsCustomerContact csCustomerContact) {
        if (null == csCustomerContact.get("id")) {
        	csCustomerContact.set("status", "1");
        	csCustomerContact.save();
        } else {
        	csCustomerContact.update();
        }
    }
    /**
     * 根据id更新客户状态
     *
     * @param id
     * @param modifier
     * @param status
     * @return
     */
    public int updateStatus(Integer id, Integer modifier, String status) {
        String sql = "update cs_customer_contact set modifier=?,modify_time=now(),rec_ver=rec_ver+1,status=? where id=?";
        return Db.update(sql, modifier, status, id);
    }
    
    /**
     * 根据id更新默认联系人
     *
     * @param id
     * @param modifier
     * @param status
     * @return
     */
    public int clearDefaultInd(Integer customer_id) {
        String sql = "update cs_customer_contact set is_default=0 where customer_id=?";
        return Db.update(sql, customer_id);
    }
    /**
     * 检查是否有重复
     * @param id
     * @param customerNo
     * @return
     */
    public boolean checkDuplicateNo(CsCustomerContact csCustomerContact) {
        List<Object> params = new ArrayList<>();
        String sql = "select count(1) from cs_customer_contact where contact_code=? and customer_id=?";
        
        params.add(csCustomerContact.getStr("contact_code"));
        params.add(csCustomerContact.getInt("customer_id"));
        
        Integer id=csCustomerContact.getInt("id");
        if (null != id) {
            sql = sql + " and id!=?";
            params.add(id);
        }
        return Db.queryLong(sql, params.toArray(new Object[params.size()])) > 0;
    }
    /**
     * 检查是否有默认联系人
     * @param id
     * @param customerNo
     * @return
     */
    public boolean checkDefault(CsCustomerContact csCustomerContact) {
        List<Object> params = new ArrayList<>();
        String sql = "select count(1) from cs_customer_contact where is_default=? and customer_id=?";
        
        params.add("1");
        params.add(csCustomerContact.getInt("customer_id"));
        
        Integer id=csCustomerContact.getInt("id");
        if (null != id) {
            sql = sql + " and id!=?";
            params.add(id);
        }
        return Db.queryLong(sql, params.toArray(new Object[params.size()])) > 0;
    }
    
    /**
     * 根据id更新默认联系人
     *
     * @param id
     * @param modifier
     * @param status
     * @return
     */
    public int deleteByCustomerId(Integer customer_id) {
        String sql = "delete from cs_customer_contact where customer_id=?";
        return Db.update(sql, customer_id);
    }
}
