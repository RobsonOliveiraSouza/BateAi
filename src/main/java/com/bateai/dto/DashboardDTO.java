package com.bateai.dto;

import lombok.Data;

@Data
public class DashboardDTO {
    private long total;
    private long aprovados;
    private long pendentes;
    private long rejeitados;

    public DashboardDTO(long total, long aprovados, long pendentes, long rejeitados) {
        this.total = total;
        this.aprovados = aprovados;
        this.pendentes = pendentes;
        this.rejeitados = rejeitados;
    }

}
