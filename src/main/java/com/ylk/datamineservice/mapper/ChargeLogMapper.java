package com.ylk.datamineservice.mapper;

import com.ylk.datamineservice.model.ChargeLog;
import com.ylk.datamineservice.model.ChargeLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChargeLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    int countByExample(ChargeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    int deleteByExample(ChargeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    int insert(ChargeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    int insertSelective(ChargeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    List<ChargeLog> selectByExample(ChargeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    ChargeLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    int updateByExampleSelective(@Param("record") ChargeLog record, @Param("example") ChargeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    int updateByExample(@Param("record") ChargeLog record, @Param("example") ChargeLogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    int updateByPrimaryKeySelective(ChargeLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_charge_log
     *
     * @mbggenerated Fri Jan 22 16:45:04 CST 2016
     */
    int updateByPrimaryKey(ChargeLog record);
}