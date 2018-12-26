﻿CREATE TABLE [CELPPM].[PPM_MM_TEMPLATE] (
    [UUID] VARCHAR (50)  NOT NULL,
    [NAME] VARCHAR (255) NOT NULL,
    [CREATED_BY]    VARCHAR (16)  NOT NULL,
    [CREATED_TS]    DATETIME  NOT NULL,
    [MODIFIED_TS]   DATETIME  NULL,
    [MODIFIED_BY]   VARCHAR (16)  NULL,
    CONSTRAINT [PK_PPM_MM_TEMPLATE] PRIMARY KEY CLUSTERED ([UUID] ASC)
);
GO
CREATE TABLE [CELPPM].[PPM_MM_TEMPLATE_PSET] (
    [UUID]     VARCHAR (50)  NOT NULL,
    [TEMPLATE_UUID] VARCHAR (50)  NOT NULL,
    [NUMBER]   VARCHAR(16)           NOT NULL,
    [NAME]     VARCHAR (255) NOT NULL,
    [COMPANY_ID]       VARCHAR (16)  NULL,
    [APPLICATION_ID]   VARCHAR (16)  NOT NULL,
    [EFF_DATE]      DATE          NULL,
    [CREATED_BY]    VARCHAR (16) NOT NULL,
    [CREATED_TS]    DATETIME  NOT NULL,
    [MODIFIED_TS]   DATETIME  NULL,
    [MODIFIED_BY]   VARCHAR (16) NULL,
    CONSTRAINT [PK_PPM_MM_TEMPLATE_PSET] PRIMARY KEY CLUSTERED ([UUID] ASC),
    CONSTRAINT [PPM_MM_TEMPLATE_PSET_FK1] FOREIGN KEY ([TEMPLATE_UUID]) REFERENCES [CELPPM].[PPM_MM_TEMPLATE] ([UUID]) ON DELETE CASCADE
);
GO
CREATE TABLE [CELPPM].[PPM_MM_IMPORT_TASK] (
    [UUID]    VARCHAR (50)  NOT NULL,
    [TYPE]            VARCHAR (16)  NOT NULL,
    [NAME]            VARCHAR (255) NOT NULL,
    [STATUS]          VARCHAR (16)  NOT NULL,
    [INPUT_FILE_NAME] VARCHAR (255) NOT NULL,
    [CREATED_BY]      VARCHAR (16)  NOT NULL,
    [CREATED_TS]      DATETIME  NOT NULL,
    [MODIFIED_TS]     DATETIME  NULL,
    [MODIFIED_BY]     VARCHAR (16)  NULL,
    CONSTRAINT [PK_PPM_MM_IMPORT_TASK] PRIMARY KEY CLUSTERED ([UUID] ASC)
);
GO
CREATE TABLE [CELPPM].[PPM_MM_IMPORT_TASK_DETAILS] (
    [UUID] VARCHAR (50)  NOT NULL,
    [IM_TASK_UUID] VARCHAR (50)  NOT NULL,
    [PSET_NUMBER]  VARCHAR (16)  NOT NULL,
    [PSET_NAME]    VARCHAR (255) NOT NULL,
    [COMPANY_ID]      VARCHAR (16)  NOT NULL,
    [APPLICATION_ID]  VARCHAR (16)  NOT NULL,
    [EFF_DATE]     DATE          NOT NULL,
    [EXP_DATE]     DATE          NULL,
    [PSET_KEY]     VARCHAR (100) NOT NULL,
    [ACTION]       VARCHAR (16) NOT NULL,
    [STATUS]       VARCHAR (16) NOT NULL,
    [RESULT]       VARCHAR (255) NOT NULL,
    [CREATED_BY] VARCHAR(16) NOT NULL, 
    [CREATED_TS] DATETIME NOT NULL, 
    [MODIFIED_TS] DATETIME NULL, 
    [MODIFIED_BY] VARCHAR(16) NULL, 
    CONSTRAINT [PK_PPM_MM_IMPORT_TASK_DETAILS] PRIMARY KEY CLUSTERED ([UUID] ASC),
    CONSTRAINT [PPM_MM_IMPORT_TASK_DETAILS_FK1] FOREIGN KEY ([IM_TASK_UUID]) REFERENCES [CELPPM].[PPM_MM_IMPORT_TASK] ([UUID]) ON DELETE CASCADE
);
GO
CREATE INDEX [IX_PPM_MM_IMPORT_TASK_DETAILS_Column] ON [CELPPM].[PPM_MM_IMPORT_TASK_DETAILS] ([IM_TASK_UUID], [PSET_NUMBER], [PSET_KEY]);
GO
CREATE TABLE [CELPPM].[PPM_MM_SCHEDULED_TASK] (
    [UUID] VARCHAR (50)  NOT NULL,
    [NAME]          VARCHAR (255) NOT NULL,
    [TYPE]          VARCHAR (16)  NOT NULL,
    [TEMPLATE_UUID] VARCHAR (50)  NULL,
    [EFF_DATE]      DATE          NOT NULL,
    [FILE_PATH]     VARCHAR (8000) NOT NULL,
    [FREQUENCY]     VARCHAR (16)  NOT NULL,
    [FREQ_PATTERN]  VARCHAR (50)  NULL,
    [REMARKS]       VARCHAR (255) NULL,
    [TASK_OPTIONS]  VARCHAR (16)  NULL,
    [STATUS]        VARCHAR (16)  NOT NULL,
    [CREATED_BY]    VARCHAR (16) NOT NULL,
    [CREATED_TS]    DATETIME  NOT NULL,
    [MODIFIED_TS]   DATETIME  NULL,
    [MODIFIED_BY]   VARCHAR (16) NULL,
    CONSTRAINT [PK_PPM_MM_SCHEDULED_TASK] PRIMARY KEY CLUSTERED ([UUID] ASC),
    CONSTRAINT [PPM_MM_SCHEDULED_TASK_FK1] FOREIGN KEY ([TEMPLATE_UUID]) REFERENCES [CELPPM].[PPM_MM_TEMPLATE] ([UUID]) ON DELETE CASCADE
);