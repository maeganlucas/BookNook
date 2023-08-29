CREATE DATABASE IF NOT EXISTS BookNook;
use BookNook;

create table if not exists Readers(
	username VARCHAR(30) primary key,
	password VARCHAR(30),
	name VARCHAR (25)
);

create table if not exists Authors(
	username VARCHAR(30) primary key,
	password VARCHAR(30),
	name VARCHAR(25)
);

create table if not exists BookAuthor(
	ISBN CHAR(13) primary key,
	title VARCHAR(250),
	author VARCHAR(100),
	publisher VARCHAR(50) default null,
	authorUsername VARCHAR(30) default null
);

create table if not exists BookDetails(
	title VARCHAR(250),
	author VARCHAR(100),
	series VARCHAR(100) default null,
	volume DECIMAL(2,1) default null,
	genre VARCHAR(50) default null,
	categories VARCHAR(100) default null,
	synopsis VARCHAR(10000) default null
);

create table if not exists Reviews(
	reviewID INTEGER,
	ISBN CHAR(13),
	storylineRating DECIMAL(3,2) default 0.00,
	plotRating DECIMAL(3,2) default 0.00,
	settingRating DECIMAL(3,2) default 0.00,
	spiceRating DECIMAL(3,2) default 0.00,
	charactersRating DECIMAL(3,2) default 0.00,
	wbRating DECIMAL(3,2) default 0.00,
	wsRating DECIMAL(3,2) default 0.00,
	dateStarted DATE default null,
	dateFinished DATE default null,
	writtenReview VARCHAR(1000) default null,
	primary key(reviewID, ISBN)
);

create table if not exists ReadsBook(
	username VARCHAR(30),
	ISBN CHAR(13),
	readingStatus VARCHAR(7),
	primary key(username, ISBN)
);

create table if not exists writesReview(
	username VARCHAR(30),
	reviewID INTEGER,
	primary key(username, reviewID)
);


-- inserting test data
-- --------------------------------------------------------
insert into Readers values
	('maeganlucas', 'meg', 'maegan'),
	('sallyrooney2002', '3005', 'esh'),
	('mandalynchance', 'ollie', 'mandalyn'),
	('leishacraven', 'strawberry', 'leisha');

-- ---------------------------------------------------------

insert into Authors values
	('hannahgrace', '123', 'hannah'),
	('rickriordan', '456', 'rick'),
	('anahuang', '789', 'ana'),
	('kieracass', '246', 'kiera'),
	('taylorjreid', '345', 'taylor');

-- ---------------------------------------------------------

insert into BookAuthor values
	('9781915593009', 'Icebreaker', 'Hannah Grace', 'Pig & Bear', 'hannahgrace'),
	('9780545614948', 'The Selection', 'Kiera Cass', 'Scholastic Press', 'kieracass'),
	('9780786838653', 'The Lightning Thief', 'Rick Riordan', 'Hyperion Books for Children', 'rickriordan'),
	('9798737416119', 'Twisted Love', 'Ana Huang', 'Boba Press', 'anahuang'),
	('9781501161933', 'The Seven Husbands of Evelyn Hugo', 'Taylor Jenkins Reid', 'Washington Square Press', 'taylorjreid');

-- ----------------------------------------------------------
insert into BookDetails values
	('Icebreaker', 'Hannah Grace', 'UCMH', 1.0, 'romance', 'sports, hockey', 'Anastasia Allen has worked her entire life for a shot at Team USA.

A competitive figure skater since she was five years old, a full college scholarship thanks to her place on the Maple Hills skating team, and a schedule that would make even the most driven person weep, Stassie comes to win.

No exceptions.

Nathan Hawkins has never had a problem he couldn’t solve. As captain of the Maple Hills Titans, he knows the responsibility of keeping the hockey team on the ice rests on his shoulders.

When a misunderstanding results in the two teams sharing a rink, and Anastasia’s partner gets hurt in the aftermath, Nate finds himself swapping his stick for tights, and one scary coach for an even scarier one.

The pair find themselves stuck together in more ways than one, but it’s fine, because Anastasia doesn’t even like hockey players…right?'),
	('The Selection', 'Kiera Cass', 'The Selection', 1.0, 'romance', 'ya, dystopian', 'For thirty-five girls, the Selection is the chance of a lifetime. The opportunity to escape the life laid out for them since birth. To be swept up in a world of glittering gowns and priceless jewels. To live in a palace and compete for the heart of gorgeous Prince Maxon.

But for America Singer, being Selected is a nightmare. It means turning her back on her secret love with Aspen, who is a caste below her. Leaving her home to enter a fierce competition for a crown she doesn’t want. Living in a palace that is constantly threatened by violent rebel attacks.

Then America meets Prince Maxon. Gradually, she starts to question all the plans she’s made for herself—and realizes that the life she’s always dreamed of may not compare to a future she never imagined.'),
	('The Lightning Thief', 'Rick Riordan', 'Percy Jackson and the Olympians', 1.0, 'fantasy', 'ya, greek mythology', 'Percy Jackson is a good kid, but he can’t seem to focus on his schoolwork or control his temper. And lately, being away at boarding school is only getting worse - Percy could have sworn his pre-algebra teacher turned into a monster and tried to kill him. When Percy’s mom finds out, she knows it’s time that he knew the truth about where he came from, and that he go to the one place he’ll be safe. She sends Percy to Camp Half Blood, a summer camp for demigods (on Long Island), where he learns that the father he never knew is Poseidon, God of the Sea. Soon a mystery unfolds and together with his friends—one a satyr and the other the demigod daughter of Athena - Percy sets out on a quest across the United States to reach the gates of the Underworld (located in a recording studio in Hollywood) and prevent a catastrophic war between the gods.'),
	('Twisted Love', 'Ana Huang', 'Twisted', 1.0, 'romance', 'brother’s best friend, millionaire', 'He has a heart of ice...but for her, he’d burn the world.

Alex Volkov is a devil blessed with the face of an angel and cursed with a past he can’t escape.

Driven by a tragedy that has haunted him for most of his life, his ruthless pursuits for success and vengeance leave little room for matters of the heart.

But when he’s forced to look after his best friend’s sister, he starts to feel something in his chest:

A crack.
A melt.
A fire that could end his world as he knew it.

***

Ava Chen is a free spirit trapped by nightmares of a childhood she can’t remember.

But despite her broken past, she’s never stopped seeing the beauty in the world…including the heart beneath the icy exterior of a man she shouldn’t want.

Her brother’s best friend.
Her neighbor.
Her savior and her downfall.

Theirs is a love that was never supposed to happen—but when it does, it unleashes secrets that could destroy them both…and everything they hold dear.'),
	('The Seven Husbands of Evelyn Hugo', 'Taylor Jenkins Reid', null, null, 'romance', 'lgbtq+, old hollywood', 'Aging and reclusive Hollywood movie icon Evelyn Hugo is finally ready to tell the truth about her glamorous and scandalous life. But when she chooses unknown magazine reporter Monique Grant for the job, no one is more astounded than Monique herself. Why her? Why now?

Monique is not exactly on top of the world. Her husband has left her, and her professional life is going nowhere. Regardless of why Evelyn has selected her to write her biography, Monique is determined to use this opportunity to jumpstart her career.

Summoned to Evelyn’s luxurious apartment, Monique listens in fascination as the actress tells her story. From making her way to Los Angeles in the 1950s to her decision to leave show business in the ‘80s, and, of course, the seven husbands along the way, Evelyn unspools a tale of ruthless ambition, unexpected friendship, and a great forbidden love. Monique begins to feel a very real connection to the legendary star, but as Evelyn’s story near its conclusion, it becomes clear that her life intersects with Monique’s own in tragic and irreversible ways.');


-- ----------------------------------------------------------

insert into Reviews values
	(1, '9781915593009', 5.00, 5.00, 4.75, 5.00, 5.00, 0.00, 5.00, '2022-09-25', '2022-09-26', null ),
	(2, '9780545614948', 5.00, 5.00, 5.00, 0.00, 4.00, 2.75, 3.00, '2023-01-01', '2023-01-05', 'liked it'),
	(3, '9781501161933', 5.00, 5.00, 5.00, 0.00, 5.00, 0.00, 5.00, '2023-02-22', '2023-02-22', 'fantastic'),
	(4, '9798737416119', 5.00, 5.00, 5.00, 5.00, 5.00, 0.00, 5.00, '2022-05-26', '2022-06-12', null);

-- -----------------------------------------------------------

insert into ReadsBook values
	('maeganlucas', '9781915593009', 'read'),
	('sallyrooney2002', '9781501161933', 'read'),
	('mandalynchance', '9798737416119', 'reading'),
	('leishacraven', '9780786838653', 'tbr'),
	('mandalynchance', '9781915593009', 'tbr'),
	('maeganlucas', '9780545614948', 'read'),
	('leishacraven', '9798737416119', 'read');

-- -----------------------------------------------------------

insert into WritesReview values
	('maeganlucas', 1),
	('maeganlucas', 2),
	('sallyrooney2002', 3),
	('leishacraven', 4);

-- ------------------------------------------------------------

-- shows initial tables

select * from Readers;
select * from Authors;
select * from BookAuthor;
select * from BookDetails;
select * from Reviews;
select * from ReadsBook;
select * from WritesReview;

-- -------------------------------------------------------------

-- testing insert

insert into Readers values
	('lylyb', 'delta122', 'lynn');

insert into Authors values
	('cassiec', 'reginald', 'cassandra');

insert into BookAuthor values
	('9781481456029', 'Clockwork Angel', 'Cassandra Clare', 'Simon and Schuster', 'cassiec');

insert into BookDetails values
	('Clockwork Angel', 'Cassandra Clare', 'The Infernal Devices', 1.0, 'fantasy', 'ya, fae', 'In a time when Shadowhunters are barely winning the fight against the forces of darkness, one battle will change the course of history forever. Welcome to the Infernal Devices trilogy, a stunning and dangerous prequel to the New York Times bestselling Mortal Instruments series.

The year is 1878. Tessa Gray descends into London’s dark supernatural underworld in search of her missing brother. She soon discovers that her only allies are the demon-slaying Shadowhunters—including Will and Jem, the mysterious boys she is attracted to. Soon they find themselves up against the Pandemonium Club, a secret organization of vampires, demons, warlocks, and humans. Equipped with a magical army of unstoppable clockwork creatures, the Club is out to rule the British Empire, and only Tessa and her allies can stop them...');

insert into Reviews values
	(5, '9781481456029', 5.00, 5.00, 4.75, 2.00, 5.00, 5.00, 4.00, '2021-05-16', '2021-06-14', null);
	
insert into WritesReview values
	 ('lylyb', 5);

insert into ReadsBook values
	('lylyb', '9781481456029', 'read');

-- -------------------------------------------------------------
-- shows new inserts

select * from Readers;
select * from Authors;
select * from BookAuthor;
select * from BookDetails;
select * from Reviews;
select * from ReadsBook;
select * from WritesReview;

-- ---------------------------------------------------

-- testing updates
update BookAuthor join BookDetails on BookAuthor.title = BookDetails.title and BookAuthor.author = BookDetails.author
set series = 'Maple Hills'
where ISBN = '9781915593009';

update Readers
set password = '2002'
where username = 'maeganlucas';

update Authors 
set name = 'taylor j.'
where username = 'taylorjreid';

update Reviews
set spiceRating = 1.25
where reviewID = 5;

update ReadsBook
set readingStatus = 'reading'
where username = 'leishacraven' and ISBN = '9780786838653';

-- ---------------------------------------------------------------

-- shows new updates
select * from Readers;
select * from Authors;
select * from BookAuthor;
select * from BookDetails;
select * from Reviews;
select * from ReadsBook;
select * from WritesReview;

-- ----------------------------------------------------------------

-- testing deletes

 delete ba.*, bd.*, r.*, wr.*, rb.* from BookAuthor ba, BookDetails bd, Reviews r, WritesReview wr, ReadsBook rb
 where ba.title = bd.title and ba.author = bd.author and ba.ISBN = r.ISBN and r.ISBN = rb.ISBN and r.reviewID = wr.reviewID and ba.ISBN = '9780545614948';

 delete rs.*, wr.*, rb.*, r.* from Readers rs, WritesReview wr, ReadsBook rb, Reviews r
 where rs.username = wr.username and wr.username = rb.username and wr.reviewID = r.reviewID and rs.username = 'leishacraven';

 delete a.* from Authors a
 where a.username = 'anahuang';

 update BookAuthor
 set authorUsername = null
 where authorUsername = 'anahuang';

 delete r.*, wr.* from Reviews r, WritesReview wr
 where r.reviewID = wr.reviewID and r.reviewID = '3';

 delete from ReadsBook
 where username = 'mandalynchance' and ISBN = '9798737416119';

-- -------------------------------------------------------------

-- shows deletes

select * from Readers;
select * from Authors;
select * from BookAuthor;
select * from BookDetails;
select * from Reviews;
select * from ReadsBook;
select * from WritesReview;


-- -------------------------------------------------------------

-- reinserts deleted entries
insert into BookAuthor values
	('9780545614948', 'The Selection', 'Kiera Cass', 'Scholastic Press', 'kieracass');

insert into BookDetails values
	('The Selection', 'Kiera Cass', 'The Selection', 1.0, 'romance', 'ya, dystopian', 'For thirty-five girls, the Selection is the chance of a lifetime. The opportunity to escape the life laid out for them since birth. To be swept up in a world of glittering gowns and priceless jewels. To live in a palace and compete for the heart of gorgeous Prince Maxon.

But for America Singer, being Selected is a nightmare. It means turning her back on her secret love with Aspen, who is a caste below her. Leaving her home to enter a fierce competition for a crown she doesn’t want. Living in a palace that is constantly threatened by violent rebel attacks.

Then America meets Prince Maxon. Gradually, she starts to question all the plans she’s made for herself—and realizes that the life she’s always dreamed of may not compare to a future she never imagined.');

insert into Readers values
	('leishacraven', 'strawberry', 'leisha');

insert into ReadsBook values
	('maeganlucas', '9780545614948', 'read'),
	('leishacraven', '9780786838653', 'reading'),
	('mandalynchance', '9798737416119', 'reading'),
	('leishacraven', '9798737416119', 'read');

insert into WritesReview values
	('maeganlucas', 2),
	('sallyrooney2002', 3),
	('leishacraven', 4);

insert into Authors values
	('anahuang', '789', 'ana');

update bookauthor 
set authorUsername = 'anahuang'
where ISBN = '9798737416119';


insert into Reviews values
	(2, '9780545614948', 5.00, 5.00, 5.00, 0.00, 4.00, 2.75, 3.00, '2023-01-01', '2023-01-05', 'liked it'),
	(3, '9781501161933', 5.00, 5.00, 5.00, 0.00, 5.00, 0.00, 5.00, '2023-02-22', '2023-02-22', 'fantastic'),
	(4, '9798737416119', 5.00, 5.00, 5.00, 5.00, 5.00, 0.00, 5.00, '2022-05-26', '2022-06-12', null);
	

-- testing display operations

select ba.ISBN, ba.title, ba.author, bd.series, bd.volume, ba.publisher, bd.genre, bd.categories, bd.synopsis
from (BookAuthor ba natural join BookDetails bd natural join ReadsBook rb)
where rb.username = 'maeganlucas' and rb.readingStatus = 'read';

select ba.ISBN, ba.title, ba.author, bd.series, bd.volume, ba.publisher, bd.genre, bd.categories, bd.synopsis
from (BookAuthor ba natural join BookDetails bd)
where ba.authorUsername = 'rickriordan';

select storylineRating, plotRating, settingRating, spiceRating, charactersRating, wbRating, wsRating 
from Reviews
where ISBN = '9781481456029';

select ba.ISBN, ba.title, ba.author, bd.series, bd.volume, ba.publisher, bd.genre, bd.categories, bd.synopsis, r.storylineRating, r.plotRating, r.settingRating, r.charactersRating, r.wbRating, r.wsRating
from (BookAuthor ba natural join BookDetails bd natural join Reviews r natural join WritesReview wr)
where wr.username = 'maeganlucas' and r.dateFinished >= '2022-06-01' and r.dateFinished <= '2023-06-01';




