INSERT INTO "musicians" ("first_name","last_name","email_address","age","style") VALUES ('James','Connolly','james.connolly@jim.com',45,'METAL');
INSERT INTO "musicians" ("first_name","last_name","email_address","age","style") VALUES ('Fredrik','Thordendal','fredrik.thordendal@meshuggah.com',53,'METAL');
INSERT INTO "musicians" ("first_name","last_name","email_address","age","style") VALUES ('Les','Claypool','les@primus.com',60,'ROCK');

INSERT INTO "instruments" ("make","model") VALUES ('Gibson','Epiphone');
INSERT INTO "instruments" ("make","model") VALUES ('Tama','Star Classic');
INSERT INTO "instruments" ("make","model") VALUES ('Yamaha','SLB');
INSERT INTO "instruments" ("make","model") VALUES ('Shure','SM58');

INSERT INTO "mus_insts" ("musician_id","instrument_id") VALUES (1,1);
INSERT INTO "mus_insts" ("musician_id","instrument_id") VALUES (1,2);
INSERT INTO "mus_insts" ("musician_id","instrument_id") VALUES (2,1);
INSERT INTO "mus_insts" ("musician_id","instrument_id") VALUES (3,3);
INSERT INTO "mus_insts" ("musician_id","instrument_id") VALUES (3,4);
