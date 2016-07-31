package com.ylk.datamineservice.mapper;

import com.ylk.datamineservice.model.ModuleInfo;
import com.ylk.datamineservice.model.ModuleInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ModuleInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    int countByExample(ModuleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    int deleteByExample(ModuleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    int insert(ModuleInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    int insertSelective(ModuleInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    List<ModuleInfo> selectByExample(ModuleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    ModuleInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    int updateByExampleSelective(@Param("record") ModuleInfo record, @Param("example") ModuleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    int updateByExample(@Param("record") ModuleInfo record, @Param("example") ModuleInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    int updateByPrimaryKeySelective(ModuleInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_module
     *
     * @mbggenerated Sun Jul 03 10:41:26 CST 2016
     */
    int updateByPrimaryKey(ModuleInfo record);
}