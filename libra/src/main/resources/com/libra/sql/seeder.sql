CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    username TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS book (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    author TEXT NOT NULL,
    title TEXT NOT NULL,
    price REAL NOT NULL,
    amount INTEGER NOT NULL,
    UNIQUE(author, title)
);

CREATE TABLE IF NOT EXISTS states (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    UNIQUE(name)
);

CREATE TABLE IF NOT EXISTS orders (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    address TEXT NOT NULL,
    book_id INTEGER NOT NULL,
    amount INTEGER NOT NULL,
    price REAL NOT NULL,
    state_id INTEGER NOT NULL
);


INSERT OR IGNORE INTO users (name, username, email, password, role_id) VALUES ('admin', 'admin', 'admin@admin.com', 'admin', 1);
INSERT OR IGNORE INTO users (name, username, email, password, role_id) VALUES ('User User', 'user', 'user@user.com', 'user', 2);

INSERT OR IGNORE INTO roles (name) VALUES ('admin');
INSERT OR IGNORE INTO roles (name) VALUES ('user');

INSERT OR IGNORE INTO book (author, title, price, amount) VALUES ('J. K. Rowling', 'Harry Potter és a Bölcsek köve', 3500, 17);
INSERT OR IGNORE INTO book (author, title, price, amount) VALUES ('J. K. Rowling', 'Harry Potter és a Titkok kamrája', 3600, 18);
INSERT OR IGNORE INTO book (author, title, price, amount) VALUES ('J. K. Rowling', 'Harry Potter és az azkabani fogoly', 3700, 19);
INSERT OR IGNORE INTO book (author, title, price, amount) VALUES ('J. K. Rowling', 'Harry Potter és a Tűz serlege', 3800, 20);
INSERT OR IGNORE INTO book (author, title, price, amount) VALUES ('J. K. Rowling', 'Harry Potter és a Főnix rendje', 3900, 21);
INSERT OR IGNORE INTO book (author, title, price, amount) VALUES ('J. K. Rowling', 'Harry Potter és a Félvér Herceg', 4000, 22);
INSERT OR IGNORE INTO book (author, title, price, amount) VALUES ('J. K. Rowling', 'Harry Potter és a Halál erkelyéi', 4100, 23);
INSERT OR IGNORE INTO book (author, title, price, amount) VALUES ('J. K. Rowling', 'Harry Potter és az elátkozott gyermek', 6000, 11);

INSERT OR IGNORE INTO states (name) VALUES ('Feldolgozás alatt');
INSERT OR IGNORE INTO states (name) VALUES ('Összekészítés');
INSERT OR IGNORE INTO states (name) VALUES ('Szállítás alatt');
INSERT OR IGNORE INTO states (name) VALUES ('Kiszállítva');