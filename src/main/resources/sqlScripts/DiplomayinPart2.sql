ALTER TABLE DE_Location
ADD Photo NVARCHAR(MAX);
GO
ALTER TABLE DE_LocationSightSeeing
DROP CONSTRAINT [FK_DE_LocationSightSeeing_C_Document];
GO
ALTER TABLE DE_LocationSightSeeing
DROP COLUMN DocumentID;
GO
DROP TABLE C_Document;
GO
CREATE TABLE DE_LocationSightSeeingPhoto
    (
      LocationSightSeeingPhotoID INT
        CONSTRAINT PK_DE_LocationSightSeeingPhoto PRIMARY KEY CLUSTERED
        IDENTITY ,
      LocationSightSeeingID INT
        CONSTRAINT FK_DE_LocationSightSeeingPhoto_DE_LocationSightSeeing
        FOREIGN KEY REFERENCES DE_LocationSightSeeing ( LocationSightSeeingID ) ,
      Photo NVARCHAR(MAX)
    );
GO
ALTER TABLE DE_Booking
ADD LocationFromID INT CONSTRAINT FK_DE_BookingFrom_DE_Location FOREIGN KEY REFERENCES DE_Location(LocationID),
LocationToID INT CONSTRAINT FK_DE_BookingTo_DE_Location FOREIGN KEY REFERENCES DE_Location(LocationID);
GO
CREATE TABLE DE_BookingSightSeeing
    (
      BookingSightSeeingID INT
        CONSTRAINT PK_DE_BookingSightSeeing PRIMARY KEY CLUSTERED
        IDENTITY ,
      BookingID INT
        CONSTRAINT FK_DE_BookingSightSeeing_DE_Booking
        FOREIGN KEY REFERENCES de_booking ( BookingID ) ,
      LocationSightSeeingID INT
        CONSTRAINT FK_DE_BookingSightSeeing_DE_LocationSightSeeing
        FOREIGN KEY REFERENCES DE_LocationSightSeeing ( LocationSightSeeingID )
    );
GO
