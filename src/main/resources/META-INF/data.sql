
INSERT INTO users (id, nickname, password, fullName) values (-1, 'qwerty', 'asdfg', 'Qwerty Asdfg');
INSERT INTO users (id, nickname, password, fullName) values (-2, 'udi', 'udi', 'udi');

INSERT INTO textHints (id, header, body, code, password) values (-1, 'Unlocked Hint test', 'This hint is already unlocked', 'testunlocked', 'notNeeded');
INSERT INTO textHints (id, header, body, code, password) values (-2, 'Locked Hint test', 'This hint is unlocked now', 'testlocked', 'sesame');
INSERT INTO textHints (id, header, body, code, password) values (-3, 'Hidden unocked Hint test', 'You can see this and this is already unlocked', 'hiddenunlocked', 'notNeeded');
INSERT INTO textHints (id, header, body, code, password) values (-4, 'Hidden Locked Hint test', 'You can see this and this hint is unlocked now', 'hiddenlocked', 'admin');

INSERT INTO USER_FOUND_HINTS(USERENTITY_ID, FOUNDHINTS_ID) values (-1, -1);
INSERT INTO USER_UNLOCKED_HINTS(USERENTITY_ID, UNLOCKEDHINTS_ID) values (-1, -1);

INSERT INTO USER_FOUND_HINTS(USERENTITY_ID, FOUNDHINTS_ID) values (-1, -2);

INSERT INTO USER_FOUND_HINTS(USERENTITY_ID, FOUNDHINTS_ID) values (-2, -4);
INSERT INTO USER_UNLOCKED_HINTS(USERENTITY_ID, UNLOCKEDHINTS_ID) values (-2, -4);
