use progettok19;

drop TABLE IF EXISTS LOT;
drop TABLE IF EXISTS BID;
drop TABLE IF EXISTS USER;
drop TABLE IF EXISTS TIMER;
drop TABLE IF EXISTS AUCTION;

CREATE TABLE USER
				( username CHAR(20),
				   pass CHAR(40),
				   loggedstatus BOOLEAN,
				   PRIMARY KEY(username));

CREATE TABLE AUCTION
				( id INTEGER,
				  closingdate DATETIME,
                  closed BOOLEAN,
				  PRIMARY KEY(id));

CREATE TABLE LOT
				( description CHAR(20),
				   vendor CHAR(20),
				   winner CHAR(20),
				   baseprice INTEGER,
				   auctionid INTEGER,
				   PRIMARY KEY(description,vendor,winner,baseprice,auctionid),
				   FOREIGN KEY (vendor) REFERENCES USER(username),
				   FOREIGN KEY (winner) REFERENCES USER(username),
				   FOREIGN KEY (auctionid) REFERENCES AUCTION(id));

CREATE TABLE BID
				( auctionid INTEGER,
				   offerer CHAR(20),
				   amount INTEGER,
				   PRIMARY KEY (auctionid,offerer,amount),
				   FOREIGN KEY (auctionid) REFERENCES AUCTION(id),
				   FOREIGN KEY (offerer) REFERENCES USER(username));

CREATE TABLE TIMER
			   ( id INTEGER,
                 millis LONG,
                 PRIMARY KEY (id),
                 FOREIGN KEY(id) REFERENCES AUCTION(id));
