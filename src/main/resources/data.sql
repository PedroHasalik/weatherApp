INSERT INTO CITY (city_key,country,localized_name,type) values ('7894','Argentina','Buenos Aires','City');

INSERT INTO CURRENT_CONDITIONS (temperature,local_observation_date,temperature_unit,weather_text) values (12,'test','test','test');

INSERT INTO DAILY_FORECAST (category,effective_date,end_date,text) values ('test','test','test','test');

INSERT INTO HOURLY_FORECAST (temperature,weather_icon,date_time,icon_phrase,temperature_unit) values (12,1,'test','test','test');
commit;