package com.auto.ball.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Andy_Lai
 * @since 2019-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ls_zero_mul_factors_new_bet_only")
public class MulFactorsNewBetOnly extends Model<MulFactorsNewBetOnly> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer isManzu;

    private String prod;

    private String modelType;

    private String matchId;

    private String league;

    private String hostGuest;

    private String fenzhong;

    private Integer isWin;

    private String hostName;

    private String guestName;

    private String scoreHost;

    private String scoreGuest;

    private String shootPositiveHost;

        /**
     * 模型类型
     */
         private String shootPositiveGuest;

    private String shootAsideHost;

    private String shootAsideGuest;

    private String scoreCha;

    private String cornerHost;

    private String cornerGuest;

    private String cornerCha;

    private String yellowHost;

    private String yellowGuest;

    private String yellowCha;

    private String redHost;

    private String redGuest;

    private String redCha;

    private String shootPositiveCha;

    private String shootAsideCha;

    private String shootCha;

    private String attackHost;

    private String attackGuest;

    private String attackCha;

    private String dangerousAttackHost;

    private String dangerousAttackGuest;

    private String dangerousAttackCha;

    private String ballRightHost;

    private String ballRightGuest;

    private String ballRightCha;

    private String oddsHost;

    private String letBall;

    private String oddsGuest;

    private String highHost;

    private String sizeBall;

    private String lowGuest;

    private String initialLetBall;

    private String initialSizeBall;

    private String realTime;

/*    private String dangerousAttackHost10;

    private String dangerousAttackGuest10;

    private String shootPositiveHost10;

    private String shootPositiveGuest10;

    private String shootAsideHost10;

    private String shootAsideGuest10;

    private String cornerHost10;

    private String cornerGuest10;

    private String scoreHost10;

    private String scoreGuest10;*/

        /**
     * 记录更新时间
     */
         private Date gmtUpdate;

        /**
     * 记录创建时间
     */
         private Date gmtCreate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
