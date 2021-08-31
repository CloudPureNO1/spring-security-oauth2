package com.security.auth.server.bean;

import lombok.*;

/**
 * <p></p>
 * <p></p>
 *
 * @author 王森明
 * @date 2021/4/26 11:28
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TokenIn implements java.io.Serializable{
    private String client_id;
    // 使用 加密的
    private String client_secret;
}
