create sequence PIB_PAGOE_SEQ;

/*==============================================================*/
/* Table: PIB_PAGOE                                             */
/*==============================================================*/
create table PIB_PAGOE
(
   PAE_CODIGO           NUMBER(18)           not null,
   PAE_IDENTI           VARCHAR2(100 CHAR)   not null,
   PAE_FECHA            DATE                 not null,
   PAE_PASPID           VARCHAR2(100 CHAR)   not null,
   PAE_ENTID            VARCHAR2(100 CHAR)   not null,
   PAE_APPID            VARCHAR2(100 CHAR)   not null,
   PAE_ORGID            VARCHAR2(100 CHAR),
   PAE_DETPAE           VARCHAR2(1000 CHAR),
   PAE_IDIOMA           VARCHAR2(2)          not null,
   PAE_NIF              VARCHAR2(10)         not null,
   PAE_NOMBRE           VARCHAR2(1000 CHAR)  not null,
   PAE_MODELO           VARCHAR2(100 CHAR)   not null,
   PAE_CONCEPTO         VARCHAR2(1000 CHAR)  not null,
   PAE_TASAID           VARCHAR2(100 CHAR),
   PAE_IMPORT           NUMBER(10)           not null,
   PAE_URLCBK           VARCHAR2(500 CHAR)   not null,
   PAE_LOCALI           VARCHAR2(100 CHAR),
   PAE_ESTADO           VARCHAR2(1)          not null,
   PAE_PASERC           VARCHAR2(100 CHAR),
   PAE_PASERM           VARCHAR2(500 CHAR),
   PAE_FECPAG           DATE,
   PAE_TOKEN            VARCHAR2(100 CHAR),
   PAE_USUCFM           VARCHAR2(100 CHAR),
   constraint PIB_PAGOE_PK primary key (PAE_CODIGO)
);

comment on table PIB_PAGOE is
'Lista de pagos electrónicos';

comment on column PIB_PAGOE.PAE_CODIGO is
'CODIGO';

comment on column PIB_PAGOE.PAE_IDENTI is
'IDENTIFICADOR (AUTOGENERADO POR PAYMENTIB)';

comment on column PIB_PAGOE.PAE_FECHA is
'FECHA CREACIÓN PAGO';

comment on column PIB_PAGOE.PAE_PASPID is
'IDENTIFICADOR PASARELA DE PAGO A UTILIZAR (ATIB, REDSYS,…)';

comment on column PIB_PAGOE.PAE_ENTID is
'IDENTIFICADOR ENTIDAD QUE GENERA EL PAGO (DIR3)';

comment on column PIB_PAGOE.PAE_APPID is
'IDENTIFICADOR APLICACIÓN QUE GENERA EL PAGO (SISTRA2,...)';

comment on column PIB_PAGOE.PAE_ORGID is
'IDENTIFICADOR ORGANO DENTRO DE LA ENTIDAD (OPCIONAL)';

comment on column PIB_PAGOE.PAE_DETPAE is
'DETALLE PAGO ELECTRÓNICO (DEPENDIENDO APLICACIÓN ORIGEN PUEDE ESTABLECER DATOS ADICIONALES AL PAGO: P.E. TRAMITE PARA ASISTENTE TRAMITACIÓN)';

comment on column PIB_PAGOE.PAE_IDIOMA is
'IDIOMA';

comment on column PIB_PAGOE.PAE_NIF is
'NIF SUJETO PASIVO';

comment on column PIB_PAGOE.PAE_NOMBRE is
'NOMBRE SUJETO PASIVO';

comment on column PIB_PAGOE.PAE_MODELO is
'MODELO PAGO';

comment on column PIB_PAGOE.PAE_CONCEPTO is
'CONCEPTO PAGO';

comment on column PIB_PAGOE.PAE_TASAID is
'IDENTIFICADOR TASA (OPCIONAL)';

comment on column PIB_PAGOE.PAE_IMPORT is
'IMPORTE (EN CENTS)';

comment on column PIB_PAGOE.PAE_URLCBK is
'URL CALLBACK APLICACIÓN ORIGEN';

comment on column PIB_PAGOE.PAE_LOCALI is
'LOCALIZADOR PAGO EN PASARELA (GENERADO POR PASARELA). GENERADO AL INICIAR PAGO.';

comment on column PIB_PAGOE.PAE_ESTADO is
'ESTADO PAGO: NO INICIADO (V) /DESCONOCIDO (X) / PAGADO (P) / NO PAGADO (N)';

comment on column PIB_PAGOE.PAE_PASERC is
'CODIGO ERROR PASARELA';

comment on column PIB_PAGOE.PAE_PASERM is
'MENSAJE ERROR PASARELA';

comment on column PIB_PAGOE.PAE_FECPAG is
'FECHA REALIZACION PAGO';

comment on column PIB_PAGOE.PAE_TOKEN is
'TOKEN REDIRECCION ENTRE APLICACIONES';

comment on column PIB_PAGOE.PAE_USUCFM is
'EN CASO DE CONFIRMACIÓN MANUAL SE INDICA EL USUARIO QUE HA CONFIRMADO EL PAGO';

/*==============================================================*/
/* Index: PIB_PAGOE_UK                                          */
/*==============================================================*/
create unique index PIB_PAGOE_UK on PIB_PAGOE (
   PAE_IDENTI ASC
);

/*==============================================================*/
/* Index: PIB_PAGOE_UK2                                         */
/*==============================================================*/
create unique index PIB_PAGOE_UK2 on PIB_PAGOE (
   PAE_TOKEN ASC
);

/*==============================================================*/
/* Index: PIB_PAGOE_UK3                                         */
/*==============================================================*/
create unique index PIB_PAGOE_UK3 on PIB_PAGOE (
   PAE_LOCALI ASC
);

/*==============================================================*/
/* Table: PIB_PROCES                                            */
/*==============================================================*/
create table PIB_PROCES
(
   PROC_IDENT           VARCHAR2(20 CHAR)    not null,
   PROC_INSTAN          VARCHAR2(50 CHAR),
   PROC_FECHA           DATE,
   constraint PIB_PROCES_PK primary key (PROC_IDENT)
);

comment on table PIB_PROCES is
'Control ejecución procesos background.
Para que una sola instancia se autoconfigure como maestro.';

comment on column PIB_PROCES.PROC_IDENT is
'Identificador fijo';

comment on column PIB_PROCES.PROC_INSTAN is
'Id instancia';

comment on column PIB_PROCES.PROC_FECHA is
'Fecha ultima verificación';

