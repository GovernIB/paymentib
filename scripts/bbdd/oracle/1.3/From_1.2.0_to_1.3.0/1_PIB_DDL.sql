ALTER TABLE PIB_PAGOE ADD PAE_REDPAS NUMBER(1) DEFAULT 0;
comment on column  PIB_PAGOE.PAE_REDPAS is 'Indica que se comienza a redireccionar a pasarela';