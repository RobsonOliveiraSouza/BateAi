package com.bateai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardDTO {
    private long total;
    private long aprovados;
    private long pendentes;
    private long rejeitados;
}
