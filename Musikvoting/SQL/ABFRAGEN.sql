
-- Tabellen anzeigen
SELECT * FROM t_gaeste;
SELECT * FROM t_gaeste_musik_votet;
SELECT * FROM t_musik;
SELECT * FROM t_parties;

 
-- Party hinzufügen
INSERT INTO t_parties
VALUES ('1', '18. Geburtstag von Raivo', '2017-07-15', '22:00', 'Berlin', 'Friedrauchstrasse 12', 'Monkey Bar', 'admin');

-- Gaeste hinzufügen
INSERT INTO t_gaeste(p_gast_nr, vorname, name, f_party_id)
VALUES (1, 'Leo', 'A.', '1'),
(2, 'Ali', 'B.', '1'),
(3, 'Hans', 'C.', '1'),
(4, 'Koala', 'D.', '1');


-- Musiktitel hinzufügen 
INSERT INTO t_musik(p_musik_nr, interpret, titel, dauer, genre, f_gast_nr)
VALUES 
(1, 'Kollegah', 'Kaiseraura', '00:06:10', 'Rap', 1),
(2, 'Peter Tosh', 'One Love', '00:04:00', 'Raggae', 2),
(3, 'Bob Marley', 'No Woman No Cry', '00:03:50', 'Raggae', 2),
(4, 'UB40', 'Red Red Wine', '00:03:50', 'Raggae', 2),
(5, 'The Wailers', 'Is This Love', '00:05:50', 'Raggae', 2),
(6, 'Jimi Hendrix', 'Hey Joe', '00:05:10', 'Rock', 4),
(7, 'Green Day', 'Know Your Enemy', '00:02:50', 'Rock', 4),
(8, 'Adel Tawil', 'Ist da jemand', '00:03:40', 'Pop', 3),
(9, 'David Bowie', 'Heroes', '00:03:30', 'Pop', 3),
(10, 'Ed Sheeran', 'Shape of You', '00:04:20', 'Pop', 3),
(11, 'Glasperlenspiel', 'Geiles Leben', '00:03:15', 'Pop', 1),
(12, 'Daddy Yankee', 'Despacito', '00:03:50', 'Pop', 1),
(13, 'Bushido', 'Sodom & Gomorra', '00:03:50', 'Rap', 1);

-- Prüfen ob Gast existiert: Ja = 1; Nein = 0;
SELECT COUNT(p_gast_nr)
FROM t_gaeste
WHERE p_gast_nr = 1 AND vorname = 'Leo' AND name = 'A.';

-- Votes hinzufügen
INSERT INTO t_gaeste_musik_votet(pf_gast_nr, pf_musik_nr, zeitpunkt)
VALUES (1, 1, CURRENT_TIMESTAMP),
(1, 1, CURRENT_TIMESTAMP+1),
(1, 1, CURRENT_TIMESTAMP+2),
(1, 1, CURRENT_TIMESTAMP+3),
(1, 1, CURRENT_TIMESTAMP+4),
(1, 1, CURRENT_TIMESTAMP+5),
(1, 1, CURRENT_TIMESTAMP+6),
(1, 1, CURRENT_TIMESTAMP+7),
(1, 1, CURRENT_TIMESTAMP+8),
(2, 1, CURRENT_TIMESTAMP+9),
(2, 2, CURRENT_TIMESTAMP+10),
(2, 3, CURRENT_TIMESTAMP+11),
(2, 4, CURRENT_TIMESTAMP+12),
(2, 5, CURRENT_TIMESTAMP+13),
(2, 6, CURRENT_TIMESTAMP+14),
(2, 7, CURRENT_TIMESTAMP+15),
(2, 8, CURRENT_TIMESTAMP+16),
(2, 9, CURRENT_TIMESTAMP+17),
(2, 10, CURRENT_TIMESTAMP+18),
(3, 1, CURRENT_TIMESTAMP+19),
(3, 1, CURRENT_TIMESTAMP+20),
(3, 2, CURRENT_TIMESTAMP+21),
(3, 2, CURRENT_TIMESTAMP+22),
(3, 3, CURRENT_TIMESTAMP+23),
(3, 3, CURRENT_TIMESTAMP+24),
(3, 4, CURRENT_TIMESTAMP+25),
(3, 4, CURRENT_TIMESTAMP+26),
(3, 5, CURRENT_TIMESTAMP+27),
(3, 5, CURRENT_TIMESTAMP+28),
(4, 13, CURRENT_TIMESTAMP+29),
(4, 13, CURRENT_TIMESTAMP+30),
(4, 2, CURRENT_TIMESTAMP+31);



-- votes anzeigen
SELECT * 
FROM t_gaeste_musik_votet;

-- Versuche:


-- Zeigt alle lieder, auch die ohne votes
SELECT (SELECT COUNT(*) FROM t_gaeste_musik_votet WHERE t_gaeste_musik_votet.pf_musik_nr= m.p_musik_nr) AS Votes,m.p_musik_nr, m.Titel, m.Interpret, m.Genre, m.f_gast_nr
FROM t_gaeste_musik_votet AS v, t_musik AS m
GROUP BY m.p_musik_nr
ORDER BY Votes DESC;

-- Zeigt alle lieder von einem spezifischen gast an, auch die ohne votes
SELECT (SELECT COUNT(*) FROM t_gaeste_musik_votet WHERE t_gaeste_musik_votet.pf_musik_nr= m.p_musik_nr) AS Votes, m.p_musik_nr, m.Titel, m.Interpret, m.Genre, m.f_gast_nr
FROM t_gaeste_musik_votet AS v, t_musik AS m
WHERE m.f_gast_nr = 1
GROUP BY m.p_musik_nr
ORDER BY Votes DESC;


-- Zeigt Playlist an ohne Berücksichtigung der ungültigen Votes
SELECT (SELECT COUNT(*) FROM t_gaeste_musik_votet AS GV2 WHERE GV.pf_musik_nr = GV2.pf_musik_nr) as Votes,  GV.pf_musik_nr, M.Interpret, M.Titel, M.Genre, M.f_gast_nr
FROM t_gaeste_musik_votet AS GV
INNER JOIN t_musik AS M
ON  GV.pf_musik_nr = M.p_musik_nr
GROUP BY pf_musik_nr
ORDER BY VOTES DESC;

-- Zeigt die lieder an fuer die eine person gevotet hat
SELECT m.Titel, m.Interpret, m.Genre FROM t_musik AS m, t_gaeste_musik_votet
WHERE p_musik_nr = t_gaeste_musik_votet.pf_musik_nr
AND t_gaeste_musik_votet.pf_gast_nr = 1;


-- Zählt Votes eines Gastes

SELECT COUNT(pf_gast_nr) FROM t_gaeste_musik_votet
WHERE pf_gast_nr = 1 -- Leo;

-- Gibt Zeitpunkt des ersten votes eines bestimmten Gastes zurück

SELECT MIN(Zeitpunkt) FROM t_gaeste_musik_votet
WHERE pf_gast_nr = 1;

-- Löscht ersten Vote (Tupel) eines bestimmten Gastes

DELETE Ursp FROM t_gaeste_musik_votet  Ursp
INNER JOIN t_gaeste_musik_votet  Mini
	ON Ursp.Zeitpunkt = Mini.Zeitpunkt

WHERE Ursp.Zeitpunkt IN (SELECT * FROM (SELECT MIN(t_gaeste_musik_votet.Zeitpunkt) FROM t_gaeste_musik_votet
WHERE pf_gast_nr = 1) x) ;

-- ersten Vote loeschen

DELETE FROM t_gaeste_musik_votet
WHERE pf_gast_nr = 1 
AND Zeitpunkt = (SELECT * FROM 
(SELECT MIN(Zeitpunkt) FROM t_gaeste_musik_votet WHERE pf_gast_nr = 1) AS p);

-- gibt die hoechste musik ID_nr aus
SELECT MAX(p_musik_nr) FROM t_musik;

-- party von bestimmten Gast bekommen
SELECT * FROM t_parties
WHERE p_party_id = (SELECT f_party_id FROM t_gaeste WHERE p_gast_nr = 1);

