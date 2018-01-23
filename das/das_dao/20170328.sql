alter table t_channel add COLUMN channel_type VARCHAR(50);
update t_channel set channel_type = 'webSite';
commit;