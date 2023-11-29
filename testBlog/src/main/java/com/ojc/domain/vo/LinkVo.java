package com.ojc.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {

    private String address;

    private String description;

    private long id;

    private String logo;

    private String name;

}
