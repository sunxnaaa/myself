package com.test.fx.systemconfig.mybatisplusconfig;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @OrderBy
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date createDate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String createUser;
    @OrderBy
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
