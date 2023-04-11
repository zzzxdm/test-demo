package com.zzz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author yangpei
 * @since 2019-09-17
 */
@Data
@Accessors(chain = true)
@TableName("company_info")
@ApiModel(value = "Company对象", description = "")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String alias;

    @ApiModelProperty(value = "公司名称")
    private String name;

    @ApiModelProperty(value = "公司地址")
    private String address;

    private Integer isTop;

    private Integer companyType;

    @ApiModelProperty(value = "公司规模")
    private String scale;

    @ApiModelProperty(value = "注册资本")
    private String registeredCapital;

    @ApiModelProperty(value = "法人")
    private String legalRepreSentative;

    @ApiModelProperty(value = "网站")
    private String website;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "企业简介")
    private String intro;

    private String unifiedSocialNumber;

    @ApiModelProperty(value = "是否更新工商数据")
    private String status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
