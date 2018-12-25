CREATE TABLE t_parties (
p_party_id INT PRIMARY KEY,
Motto VARCHAR(30),
Datum DATE,
Einlass TIME,
Ort VARCHAR(20),
Adresse VARCHAR(50),
Location VARCHAR(75),
AdminKey CHAR(5) UNIQUE
-- Eintrittspreis DECIMAL(5,2)
);

CREATE TABLE t_gaeste (
p_gast_nr INT PRIMARY KEY,
Vorname VARCHAR(30) NOT NULL,
Name VARCHAR(30) NOT NULL,
f_party_id INT,
CONSTRAINT fk_t_gaeste FOREIGN KEY (f_party_id) REFERENCES t_parties (p_party_id) 
ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE t_musik (
p_musik_nr INT PRIMARY KEY,
Interpret VARCHAR(30) NOT NULL,
Titel VARCHAR(30) NOT NULL,
Dauer TIME NOT NULL,
Genre VARCHAR(20) NOT NULL,
f_gast_nr INT NOT NULL,
CHECK (DAUER<10),
CONSTRAINT fk_t_musik FOREIGN KEY (f_gast_nr) REFERENCES t_gaeste (p_gast_nr) 
ON UPDATE CASCADE ON DELETE NO ACTION
);

CREATE TABLE t_gaeste_musik_votet (
pf_gast_nr INT REFERENCES t_gaeste (p_gast_nr) ON UPDATE CASCADE ON DELETE NO ACTION,
pf_musik_nr INT REFERENCES t_musik (p_musik_nr) ON UPDATE CASCADE ON DELETE CASCADE,
Zeitpunkt TIMESTAMP PRIMARY KEY
);

-- Party hinzufügen
INSERT INTO t_parties
VALUES ('1', '18. Geburtstag von Raivo', '2017-07-15', '22:00', 'Berlin', 'Friedrauchstrasse 12', 'Monkey Bar', 'admin'); 


