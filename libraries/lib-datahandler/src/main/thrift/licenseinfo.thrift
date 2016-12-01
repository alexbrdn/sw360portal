/*
 * Copyright Siemens AG, 2016. Part of the SW360 Portal Project.
 * With contributions by Bosch Software Innovations GmbH, 2016.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
include "users.thrift"
include "components.thrift"

namespace java org.eclipse.sw360.datahandler.thrift.licenseinfo
namespace php sw360.thrift.licenseinfo

typedef components.Release Release
typedef components.Attachment Attachment
typedef users.User User

enum LicenseInfoRequestStatus{
    SUCCESS = 0,
    NO_APPLICABLE_SOURCE = 1,
    FAILURE = 2,
}

struct OutputFormatInfo{
    1: optional string fileExtension,
    2: optional string description,
    3: optional string generatorClassName,
    4: bool isOutputBinary
}

struct LicenseNameWithText{
    1: optional string licenseName,
    2: optional string licenseText,
    3: optional i32 id,
}

struct LicenseInfo {
    10: optional list<string> filenames, // actual sources used
    11: required string filetype, // actual parser type used

    20: optional set<string> copyrights,
    21: optional set<LicenseNameWithText> licenseNamesWithTexts,
}

struct LicenseInfoParsingResult {
    1: required LicenseInfoRequestStatus status,
    2: optional string message,
    3: optional LicenseInfo licenseInfo,

    // identifying data of the scanned release, if applicable
    30: optional string vendor,
    31: optional string name,
    32: optional string version,
}

service LicenseInfoService {

    /**
     * Returns the CLI contained in the attachment, if any
     * */
    LicenseInfoParsingResult getLicenseInfoForAttachment(1: Attachment attachment);

    /**
     * Returns the CLI for the given release.
     * */
    LicenseInfoParsingResult getLicenseInfoForRelease(1: Release release);

    /**
     * get a copyright and license information file on all linked releases and linked releases of linked projects (recursively)
     * output format as specified by outputType
     */
    string getLicenseInfoFileForProject(1: string projectId, 2: User user, 3: string outputType);

    binary getLicenseInfoFileForProjectAsBinary(1: string projectId, 2: User user, 3: string outputType);

    /**
      * returns all available output types
      */
    list<OutputFormatInfo> getPossibleOutputFormats();

    /**
     * returns file extension that is applicable when licenseInfo is generated by the generator class
     */
    OutputFormatInfo getOutputFormatInfoForGeneratorClass(1: string generatorClassName);

}
