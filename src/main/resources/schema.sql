CREATE TABLE "address" (
    "HouseNumber" INT PRIMARY KEY NOT NULL,
    "StreetName" VARCHAR
);

-- CREATE TABLE "Cities" (
--     "Zip" INT NOT NULL,
--     "CityName" VARCHAR NOT NULL,
--     "HouseNumber" INT NOT NULL,
--     CONSTRAINT "Entry_Number" PRIMARY KEY ("Zip"),
--     CONSTRAINT "FK_AddressId" FOREIGN KEY ("HouseNumber") REFERENCES "address" ("HouseNumber") ON DELETE NO ACTION ON UPDATE NO ACTION
-- );

-- CREATE INDEX "AFK_AddressId" ON "Zip" ("HouseNumber");

INSERT INTO "address" VALUES (101,'Main Street');
INSERT INTO "address" VALUES (102,'Memorial Lane');