alter table PIB_PAGOE
  add PAE_IDTRA varchar2(50) null;

alter table PIB_PAGOE
  add PAE_VERTRA number(2) null;

comment on column PIB_PAGOE.PAE_IDTRA is
  'IDENTIFICADOR TR�MITE';

comment on column PIB_PAGOE.PAE_VERTRA is
  'VERSI�N TR�MITE';

ALTER TABLE pib_pagoe MODIFY (
    pae_idtra VARCHAR2(100 CHAR)
);
