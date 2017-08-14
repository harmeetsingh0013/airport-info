# --- !Ups

INSERT INTO countries VALUES('302556', 'AO', 'Angola', 'AF', 'http://en.wikipedia.org/wiki/Angola', NULL);
INSERT INTO countries VALUES('302557', 'BF', 'Burkina Faso', 'AF', 'http://en.wikipedia.org/wiki/Burkina_Faso', NULL);

INSERT INTO airports VALUES (9, 'FN18', 'small_airport', 'Matala Airport', -14.727499961853027, 15.013999938964844, 4120, 'AF', 'AO', 'AO-HUI', 'Matala', 'no', 'FN18', NULL, 'FN18', NULL, NULL, NULL);
INSERT INTO airports VALUES (10, 'FN19', 'small_airport', 'Cabo Ledo Airport', -9.653050422668457, 13.260600090026855, 360, 'AF', 'AO', 'AO-BGO', 'Cabo Ledo', 'no', 'FN19', NULL, 'FN19', NULL, NULL, NULL);
INSERT INTO airports VALUES (2934, 'FNBC', 'medium_airport', 'Mbanza Congo Airport', -6.269899845123291, 14.246999740600586, 1860, 'AF', 'AO', 'AO-ZAI', 'Mbanza Congo', 'yes', 'FNBC', 'SSY', NULL, NULL, 'http://en.wikipedia.org/wiki/Mbanza_Congo_Airport', NULL);
INSERT INTO airports VALUES (2088, 'DFFD', 'medium_airport', 'Ouagadougou Airport', 12.35319995880127, -1.5124200582504272, 1037, 'AF', 'BF', 'BF-KAD', 'Ouagadougou', 'yes', 'DFFD', 'OUA', NULL, NULL, 'http://en.wikipedia.org/wiki/Ouagadougou_Airport', NULL);
INSERT INTO airports VALUES (2089, 'DFOO', 'medium_airport', 'Bobo Dioulasso Airport', 11.160099983215332, -4.33096981048584, 1511, 'AF', 'BF', 'BF-HOU', 'Bobo Dioulasso', 'yes', 'DFOO', 'BOY', NULL, NULL, 'http://en.wikipedia.org/wiki/Bobo_Dioulasso_Airport', NULL);
INSERT INTO airports VALUES (6359, 'TAPA', 'medium_airport', 'V.C. Bird International Airport', 17.1367, -61.792702, 62, 'NA', 'AG', 'AG-03', 'St. George', 'yes', 'TAPA', 'ANU', NULL, NULL, 'http://en.wikipedia.org/wiki/VC_Bird_International_Airport', NULL);
INSERT INTO airports VALUES (35326, 'TAPT', 'small_airport', 'Coco Point Lodge Airstrip', 17.555599, -61.765301, 15, 'NA', 'AG', 'AG-10', 'Coco Point', 'no', 'TAPT', NULL, NULL, NULL, NULL, NULL);

INSERT INTO runways VALUES (232902, 9, 'FN18', 8015, 98, 'ASP', 0, 0, '10', -14.7265, 15.0028, NULL, 95, NULL, '28', -14.7286, 15.0253, NULL, 275, NULL);
INSERT INTO runways VALUES (232903, 10, 'FN19', 9830, 148, 'ASP', 0, 0, '08', -9.65707, 13.2476, NULL, 72, NULL, '26', -9.64903, 13.2737, NULL, 252, NULL);
INSERT INTO runways VALUES (232881, 2934, 'FNBC', 5905, 98, 'ASP', 0, 0, '16', -6.26246, 14.2436, 1860, 155, NULL, '34', -6.27733, 14.2505, 1860, 335, NULL);
INSERT INTO runways VALUES (246023, 2088, 'DFFD', 6269, 98, 'LAT', 0, 0, '04R', 12.3443, -1.51578, 1040, 35, NULL, '22L', 12.3584, -1.50561, 994, 215, NULL);
INSERT INTO runways VALUES (246024, 2088, 'DFFD', 9934, 148, 'ASP', 1, 0, '04L', 12.3417, -1.52025, 1034, 33, NULL, '22R', 12.3644, -1.50479, 982, 213, NULL);
INSERT INTO runways VALUES (246022, 2089, 'DFOO', 10826, 148, 'ASP', 1, 0, '06', 11.1511, -4.34291, 1417, 53, NULL, '24', 11.169, -4.31903, 1483, 233, NULL);
INSERT INTO runways VALUES (232759, 6359, 'TAPA', 9003, 148, 'ASP', 1, 0, '07', 17.1302, -61.8037, 58, 58.4, 656, '25', 17.1432, -61.7817, 17, 238.4, NULL);