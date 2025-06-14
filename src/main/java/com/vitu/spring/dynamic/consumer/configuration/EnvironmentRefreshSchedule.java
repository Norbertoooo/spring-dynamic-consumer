package com.vitu.spring.dynamic.consumer.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EnvironmentRefreshSchedule {

    private final ContextRefresher contextRefresher;

    public EnvironmentRefreshSchedule(@Qualifier("configDataContextRefresher") ContextRefresher contextRefresher) {
        this.contextRefresher = contextRefresher;
    }

    /**
     * Método agendado para executar conforme a expressão cron definida em
     * 'app.scheduler.refresh.cron' nas propriedades da aplicação.
     * <p>
     * Responsável por atualizar dinamicamente as propriedades de ambiente em tempo de execução.
     * Registra uma mensagem de log indicando o início do processo de atualização e invoca o
     * ContextRefresher para aplicar as novas configurações no contexto da aplicação.
     *
     * @Scheduled(cron = "${app.scheduler.refresh.cron}")
     * A expressão cron determina a frequência de execução deste método.
     */
    @Scheduled(cron = "${app.scheduler.refresh.cron}")
    public void refreshEnvironment() {
        log.info("Refreshing environment propertiese");
        contextRefresher.refresh();
    }
}
