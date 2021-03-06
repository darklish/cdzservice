package com.ylk.datamineservice.mapper;

import com.ylk.datamineservice.model.CardInfo;
import com.ylk.datamineservice.model.CardInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CardInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    int countByExample(CardInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    int deleteByExample(CardInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    int insert(CardInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    int insertSelective(CardInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    List<CardInfo> selectByExample(CardInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    CardInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    int updateByExampleSelective(@Param("record") CardInfo record, @Param("example") CardInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    int updateByExample(@Param("record") CardInfo record, @Param("example") CardInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    int updateByPrimaryKeySelective(CardInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ylk_card
     *
     * @mbggenerated Wed Jan 20 11:48:01 CST 2016
     */
    int updateByPrimaryKey(CardInfo record);
}