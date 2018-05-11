CREATE TABLE DE_LocationTrip
    (
		LocationTripID INT
        CONSTRAINT PK_DE_Trip PRIMARY KEY CLUSTERED
        IDENTITY ,
		LocationID INT
        CONSTRAINT FK_DE_LocationTrip_DE_Location
        FOREIGN KEY REFERENCES DE_Location ( LocationID ) ,
		Title NVARCHAR(500),
		StartDate DateTime,
		Details NVARCHAR(MAX),
		TicketsCount INT,
    );
GO
Create Table DE_LocationTripCheckpoint
	(
		LocationTripCheckPointID INT
        CONSTRAINT PK_DE_LocationTripCheckPointID PRIMARY KEY CLUSTERED
        IDENTITY ,
		LocationTripID INT
        CONSTRAINT FK_DE_LocationTrip_DE_LocationTripCheckPoint
        FOREIGN KEY REFERENCES DE_LocationTrip ( LocationTripID ) ON DELETE CASCADE,
		LocationSightSeeingID INT
        CONSTRAINT FK_DE_LocationSightSeeing_DE_LocationTripCheckPoint
        FOREIGN KEY REFERENCES DE_LocationSightSeeing ( LocationSightSeeingID ) ON DELETE CASCADE,
		VisitOrder INT
	);
GO
Create Table DE_Booking
	(
		BookingID INT
        CONSTRAINT PK_DE_Booking PRIMARY KEY CLUSTERED
        IDENTITY ,
		LocationTripID INT
        CONSTRAINT FK_DE_LocationTrip_DE_Booking
        FOREIGN KEY REFERENCES DE_LocationTrip ( LocationTripID ) ON DELETE CASCADE,
		UserID INT
        CONSTRAINT FK_DE_User_DE_Booking
        FOREIGN KEY REFERENCES C_User ( UserID ) ON DELETE CASCADE,
	);