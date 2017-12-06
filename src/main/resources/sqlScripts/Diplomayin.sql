IF NOT EXISTS ( SELECT  1
                FROM    sys.databases
                WHERE   name = 'TravelingAgency' )
    BEGIN
        CREATE DATABASE TravelingAgency;
    END;
GO
USE TravelingAgency;
IF OBJECT_ID('C_Gender') IS NULL
    BEGIN
        CREATE TABLE dbo.C_Gender
            (
              GenderID INT CONSTRAINT PK_C_Gender PRIMARY KEY CLUSTERED
                           IDENTITY ,
              Name NVARCHAR(250)
            );
        INSERT  INTO dbo.C_Gender
                ( Name )
        VALUES  ( 'Male' ) ,
                ( 'Female' );
    END;
GO
IF OBJECT_ID('C_User') IS NULL
    BEGIN
        CREATE TABLE dbo.C_User
            (
              UserID INT CONSTRAINT PK_C_User PRIMARY KEY CLUSTERED
                         IDENTITY ,
              FirstName NVARCHAR(250) ,
              LastName NVARCHAR(250) ,
              DateOfBirth DATETIME ,
              PhoneNumber NVARCHAR(50) ,
              Address NVARCHAR(2000) ,
              Email NVARCHAR(200) ,
              GenderID INT
                CONSTRAINT FK_C_User_C_Gender
                FOREIGN KEY REFERENCES dbo.C_Gender ( GenderID )
            );
        SET IDENTITY_INSERT dbo.C_User ON;
        INSERT  INTO dbo.C_User
                ( UserID ,
                  FirstName ,
                  LastName ,
                  DateOfBirth ,
                  PhoneNumber ,
                  Address ,
                  Email ,
                  GenderID
		        )
        VALUES  ( -1 ,
                  N'System Account' ,
                  N'System Account' ,
                  GETDATE() ,
                  N'098205200' ,
                  N'Ejmiatsin' ,
                  N'levon.khachatryan.1996.db@gmail.com' ,
                  1
		        );
        SET IDENTITY_INSERT dbo.C_User OFF;
    END;
GO
IF OBJECT_ID('C_Role') IS NULL
    BEGIN
        CREATE TABLE dbo.C_Role
            (
              RoleID INT CONSTRAINT PK_C_Role PRIMARY KEY CLUSTERED
                         IDENTITY ,
              Name NVARCHAR(250)
            );
        INSERT  INTO dbo.C_Role
                ( Name )
        VALUES  ( 'Administrator' ) ,
                ( 'Public Role' ) ,
                ( 'Users' );
    END;
GO
IF OBJECT_ID('UserRole') IS NULL
    BEGIN
        CREATE TABLE dbo.UserRole
            (
              UserRoleID INT CONSTRAINT PK_UserRole PRIMARY KEY CLUSTERED
                             IDENTITY ,
              UserID INT
                CONSTRAINT FK_UserRole_C_User
                FOREIGN KEY REFERENCES dbo.C_User ( UserID ) ,
              RoleID INT
                CONSTRAINT FK_UserRole_C_Role
                FOREIGN KEY REFERENCES dbo.C_Role ( RoleID )
            );
        INSERT  INTO dbo.UserRole
                ( UserID, RoleID )
        VALUES  ( -1, 1 );
    END;
GO
IF OBJECT_ID('C_Country') IS NULL
    BEGIN
        CREATE TABLE dbo.C_Country
            (
              CountryID INT CONSTRAINT PK_C_Country PRIMARY KEY CLUSTERED
                            IDENTITY ,
              Name NVARCHAR(250)
            );
		----
		----
        SET IDENTITY_INSERT [dbo].[C_Country] ON;
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 1, N'Afghanistan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 2, N'Albania' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 3, N'Algeria' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 4, N'Andorra' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 5, N'Angola' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 6, N'Antigua and Barbuda' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 7, N'Argentina' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 8, N'Armenia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 9, N'Aruba' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 10, N'Australia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 11, N'Austria' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 12, N'Azerbaijan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 13, N'Bahamas, The' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 14, N'Bahrain' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 15, N'Bangladesh' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 16, N'Barbados' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 17, N'Belarus' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 18, N'Belgium' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 19, N'Belize' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 20, N'Benin' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 21, N'Bhutan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 22, N'Bolivia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 23, N'Bosnia and Herzegovina' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 24, N'Botswana' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 25, N'Brazil' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 26, N'Brunei' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 27, N'Bulgaria' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 28, N'Burkina Faso' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 29, N'Burma' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 30, N'Burundi' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 31, N'Cabo Verde' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 32, N'Cambodia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 33, N'Cameroon' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 34, N'Canada' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 35, N'Central African Republic' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 36, N'Chad' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 37, N'Chile' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 38, N'China' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 39, N'Colombia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 40, N'Comoros' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID] ,
                  [Name]
                )
        VALUES  ( 41 ,
                  N'Congo, Democratic Republic of the'
                );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 42, N'Congo, Republic of the' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 43, N'Costa Rica' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 44, N'Cote d''Ivoire' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 45, N'Croatia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 46, N'Cuba' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 47, N'Curacao' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 48, N'Cyprus' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 49, N'Czechia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 50, N'Denmark' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 51, N'Djibouti' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 52, N'Dominica' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 53, N'Dominican Republic' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 54, N'East Timor' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 55, N'Ecuador' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 56, N'Egypt' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 57, N'El Salvador' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 58, N'Equatorial Guinea' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 59, N'Eritrea' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 60, N'Estonia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 61, N'Ethiopia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 62, N'Fiji' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 63, N'Finland' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 64, N'France' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 65, N'Gabon' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 66, N'Gambia, The' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 67, N'Georgia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 68, N'Germany' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 69, N'Ghana' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 70, N'Greece' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 71, N'Grenada' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 72, N'Guatemala' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 73, N'Guinea' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 74, N'Guinea-Bissau' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 75, N'Guyana' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 76, N'Haiti' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 77, N'Holy See' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 78, N'Honduras' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 79, N'Hong Kong' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 80, N'Hungary' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 81, N'Iceland' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 82, N'India' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 83, N'Indonesia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 84, N'Iran' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 85, N'Iraq' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 86, N'Ireland' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 87, N'Israel' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 88, N'Italy' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 89, N'Jamaica' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 90, N'Japan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 91, N'Jordan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 92, N'Kazakhstan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 93, N'Kenya' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 94, N'Kiribati' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 95, N'Korea, North' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 96, N'Korea, South' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 97, N'Kosovo' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 98, N'Kuwait' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 99, N'Kyrgyzstan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 100, N'Laos' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 101, N'Latvia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 102, N'Lebanon' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 103, N'Lesotho' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 104, N'Liberia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 105, N'Libya' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 106, N'Liechtenstein' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 107, N'Lithuania' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 108, N'Luxembourg' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 109, N'Macau' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 110, N'Macedonia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 111, N'Madagascar' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 112, N'Malawi' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 113, N'Malaysia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 114, N'Maldives' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 115, N'Mali' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 116, N'Malta' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 117, N'Marshall Islands' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 118, N'Mauritania' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 119, N'Mauritius' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 120, N'Mexico' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 121, N'Micronesia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 122, N'Moldova' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 123, N'Monaco' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 124, N'Mongolia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 125, N'Montenegro' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 126, N'Morocco' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 127, N'Mozambique' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 128, N'Namibia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 129, N'Nauru' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 130, N'Nepal' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 131, N'Netherlands' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 132, N'New Zealand' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 133, N'Nicaragua' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 134, N'Niger' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 135, N'Nigeria' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 136, N'North Korea' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 137, N'Norway' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 138, N'Oman' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 139, N'Pakistan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 140, N'Palau' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 141, N'Palestinian Territories' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 142, N'Panama' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 143, N'Papua New Guinea' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 144, N'Paraguay' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 145, N'Peru' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 146, N'Philippines' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 147, N'Poland' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 148, N'Portugal' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 149, N'Qatar' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 150, N'Romania' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 151, N'Russia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 152, N'Rwanda' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 153, N'Saint Kitts and Nevis' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 154, N'Saint Lucia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID] ,
                  [Name]
                )
        VALUES  ( 155 ,
                  N'Saint Vincent and the Grenadines'
                );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 156, N'Samoa' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 157, N'San Marino' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 158, N'Sao Tome and Principe' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 159, N'Saudi Arabia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 160, N'Senegal' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 161, N'Serbia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 162, N'Seychelles' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 163, N'Sierra Leone' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 164, N'Singapore' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 165, N'Sint Maarten' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 166, N'Slovakia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 167, N'Slovenia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 168, N'Solomon Islands' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 169, N'Somalia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 170, N'South Africa' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 171, N'South Korea' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 172, N'South Sudan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 173, N'Spain' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 174, N'Sri Lanka' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 175, N'Sudan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 176, N'Suriname' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 177, N'Swaziland' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 178, N'Sweden' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 179, N'Switzerland' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 180, N'Syria' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 181, N'Taiwan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 182, N'Tajikistan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 183, N'Tanzania' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 184, N'Thailand' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 185, N'Timor-Leste' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 186, N'Togo' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 187, N'Tonga' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 188, N'Trinidad and Tobago' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 189, N'Tunisia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 190, N'Turkey' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 191, N'Turkmenistan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 192, N'Tuvalu' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 193, N'Uganda' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 194, N'Ukraine' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 195, N'United Arab Emirates' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 196, N'United Kingdom' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 197, N'United States' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 198, N'Uruguay' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 199, N'Uzbekistan' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 200, N'Vanuatu' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 201, N'Venezuela' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 202, N'Vietnam' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 203, N'Yemen' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 204, N'Zambia' );
        INSERT  INTO [dbo].[C_Country]
                ( [CountryID], [Name] )
        VALUES  ( 205, N'Zimbabwe' );
        SET IDENTITY_INSERT [dbo].[C_Country] OFF;
		----
		----
    END;
GO
IF OBJECT_ID('C_State') IS NULL
    BEGIN
        CREATE TABLE dbo.C_State
            (
              StateID INT CONSTRAINT PK_C_State PRIMARY KEY CLUSTERED
                          IDENTITY ,
              Name NVARCHAR(250) ,
              CountryID INT
                CONSTRAINT FK_C_State_C_Country
                FOREIGN KEY REFERENCES dbo.C_Country ( CountryID )
            );
		----
		----
        SET IDENTITY_INSERT [dbo].[C_State] ON;
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 1, N' Alabama', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 2, N' Alaska', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 3, N' Arizona', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 4, N'Arkansas', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 5, N'California', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 6, N'Colorado', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 7, N'Connecticut', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 8, N'Delaware', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 9, N'District Of Columbia', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 10, N'Florida', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 11, N'Georgia', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 12, N'Hawaii', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 13, N'Idaho', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 14, N'Illinois', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 15, N'Indiana', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 16, N'Iowa', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 17, N'Kansas', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 18, N'Kentucky', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 19, N'Louisiana', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 20, N'Maine', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 21, N'Maryland', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 22, N'Massachusetts', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 23, N'Michigan', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 24, N'Minnesota', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 25, N'Mississippi', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 26, N'Missouri', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 27, N'Montana', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 28, N'Nebraska', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 29, N'Nevada', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 30, N'New Hampshire', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 31, N'New Jersey', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 32, N'New Mexico', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 33, N'New York', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 34, N'North Carolina', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 35, N'North Dakota', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 36, N'Ohio', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 37, N'Oklahoma', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 38, N'Oregon', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 39, N'Pennsylvania', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 40, N'Rhode Island', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 41, N'South Carolina', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 42, N'South Dakota', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 43, N'Tennessee', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 44, N'Texas', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 45, N'Utah', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 46, N'Vermont', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 47, N'Virginia', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 48, N'Washington', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 49, N'West Virginia', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 50, N'Wisconsin', 197 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 51, N'Wyoming', 197 );
		--
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 52, N'Aragatsotn', 8 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 53, N'Ararat', 8 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 54, N'Armavir', 8 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 55, N'Gegharkunik', 8 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 56, N'Kotayk', 8 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 57, N'Lori', 8 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 58, N'Shirak', 8 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 59, N'Syunik', 8 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 60, N'Tavush', 8 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 61, N'Vayots Dzor', 8 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 62, N'Yerevan', 8 );
		--
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 63, N'Baden-Württemberg', 68 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 64, N'Bayern', 68 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 65, N'Berlin', 68 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 66, N'Brandenburg', 68 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 67, N'Bremen', 68 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 68, N'Hamburg', 68 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 69, N'Mecklenburg-Vorpommern', 68 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 70, N'Niedersachsen', 68 );
        INSERT  INTO [dbo].[C_State]
                ( [StateID], [Name], [CountryID] )
        VALUES  ( 71, N'Nordrhein-Westfalen', 68 );
        SET IDENTITY_INSERT [dbo].[C_State] OFF;
		----
		----
    END;
GO
IF OBJECT_ID('DE_LocationPublishedItem') IS NULL
    BEGIN
        CREATE TABLE dbo.DE_LocationPublishedItem
            (
              LocationID INT PRIMARY KEY CLUSTERED ,
              LocationInstanceID INT NOT NULL ,
              CONSTRAINT FK_DE_LocationPublishedItem_DE_Location FOREIGN KEY ( LocationID ) REFERENCES DE_Location ( LocationID )
            );
    END;
GO
IF OBJECT_ID('DE_Location') IS NULL
    BEGIN
        CREATE TABLE dbo.DE_Location
            (
              LocationID INT CONSTRAINT PK_DE_Location PRIMARY KEY CLUSTERED
                             IDENTITY ,
              LocationInstanceID INT ,
              LocationName NVARCHAR(1000) ,
              CountryID INT
                CONSTRAINT FK_Location_C_Country
                FOREIGN KEY REFERENCES dbo.C_Country ( CountryID ) ,
              StateID INT
                CONSTRAINT FK_Location_C_State
                FOREIGN KEY REFERENCES dbo.C_State ( StateID )
            );
    END;
GO
IF OBJECT_ID('C_DocumentType') IS NULL
    BEGIN
        CREATE TABLE dbo.C_DocumentType
            (
              DocumentTypeID INT
                CONSTRAINT PK_C_DocumentType PRIMARY KEY CLUSTERED
                IDENTITY ,
              Name NVARCHAR(50)
            );
        SET IDENTITY_INSERT [dbo].[C_DocumentType] ON;
        INSERT  INTO [dbo].[C_DocumentType]
                ( [DocumentTypeID], [Name] )
        VALUES  ( 1, N'Excel' );
        INSERT  INTO [dbo].[C_DocumentType]
                ( [DocumentTypeID], [Name] )
        VALUES  ( 2, N'Word' );
        INSERT  INTO [dbo].[C_DocumentType]
                ( [DocumentTypeID], [Name] )
        VALUES  ( 3, N'PPT' );
        INSERT  INTO [dbo].[C_DocumentType]
                ( [DocumentTypeID], [Name] )
        VALUES  ( 4, N'PDF' );
        INSERT  INTO [dbo].[C_DocumentType]
                ( [DocumentTypeID], [Name] )
        VALUES  ( 5, N'TXT' );
        INSERT  INTO [dbo].[C_DocumentType]
                ( [DocumentTypeID], [Name] )
        VALUES  ( 6, N'CSV' );
        INSERT  INTO [dbo].[C_DocumentType]
                ( [DocumentTypeID], [Name] )
        VALUES  ( 7, N'HTML' );
        INSERT  INTO [dbo].[C_DocumentType]
                ( [DocumentTypeID], [Name] )
        VALUES  ( 8, N'Images' );
        INSERT  INTO [dbo].[C_DocumentType]
                ( [DocumentTypeID], [Name] )
        VALUES  ( 10, N'Other' );
        SET IDENTITY_INSERT [dbo].[C_DocumentType] OFF;
    END;
GO
IF OBJECT_ID('C_Document') IS NULL
    BEGIN
        CREATE TABLE [dbo].C_Document
            (
              [FullPath] [NVARCHAR](1000) NULL ,
              [FileName] [NVARCHAR](300) NULL ,
              [Type] [VARCHAR](100) NULL ,
              [Body] [NVARCHAR](MAX) NULL ,
              [UserID] [INT] NULL ,
              [FolderPath] [NVARCHAR](500) NULL ,
              [UploadDate] [DATETIME] NULL ,
              [SessionID] [VARCHAR](50) NULL ,
              [IsTempSaved] [INT] NULL ,
              [Title_ENG] [NVARCHAR](500) NULL ,
              [Description_ENG] [NVARCHAR](2000) NULL ,
              [Size] [DECIMAL](18, 2) NULL ,
              [OriginalDocumentName] [NVARCHAR](800) NULL ,
              [ThumbnailPreviewWidth] [INT] NULL ,
              [ThumbnailPreviewHeight] [INT] NULL ,
              [DocumentTypeID] [INT]
                CONSTRAINT FK_C_Document_C_DocumentType
                FOREIGN KEY REFERENCES dbo.C_DocumentType ( DocumentTypeID ) ,
              [IsShared] [INT] NULL ,
              [Keyword] [NVARCHAR](2000) NULL ,
              [IsActive] [INT] NULL ,
              [DocumentID] [INT]
                CONSTRAINT [PK_C_Document] PRIMARY KEY CLUSTERED
                IDENTITY ,
              [Details] [NVARCHAR](MAX) NULL ,
              [OtherDocumentType] [NVARCHAR](500) NULL ,
              [Disposition] [NVARCHAR](2000) NULL
            );
    END;
GO
IF OBJECT_ID('DE_LocationSightSeeing') IS NULL
    BEGIN
        CREATE TABLE dbo.DE_LocationSightSeeing
            (
              LocationSightSeeingID INT
                CONSTRAINT PK_DE_LocationSightSeeing PRIMARY KEY CLUSTERED
                IDENTITY ,
              LocationID INT
                CONSTRAINT FK_DE_LocationSightSeeing_DE_Location
                FOREIGN KEY REFERENCES dbo.DE_Location ( LocationID ) ,
              SightSeeingName NVARCHAR(1000) ,
              Details NVARCHAR(MAX) ,
              Comment NVARCHAR(2000) ,
              DocumentID INT
                CONSTRAINT FK_DE_LocationSightSeeing_C_Document
                FOREIGN KEY REFERENCES dbo.C_Document ( DocumentID ),
			  
            );
    END;
GO
IF OBJECT_ID('DE_Booking') IS NULL
    BEGIN
        CREATE TABLE dbo.DE_Booking  ---???
            (
              BookingID INT CONSTRAINT PK_DE_Booking PRIMARY KEY CLUSTERED IDENTITY,
			  
            );
    END;
GO
