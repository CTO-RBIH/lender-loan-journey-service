package in.rbihub.utils;


import static in.rbihub.utils.LenderLoanJourneyConstants.*;

public enum MasterDataServiceEnum {
        TN_SURVEYNUMBER(TN, SURVEYNUMBER),
        TN_SUBDIVNUMBER(TN, SUBDIVNUMBER);


    private final String provider;
        private final String servicename;

    MasterDataServiceEnum(String provider, String servicename) {
            this.provider = provider;
            this.servicename = servicename;
        }

        public static  MasterDataServiceEnum from(String provider, String servicename) {
            for ( MasterDataServiceEnum mds : values()) {
                if (mds.provider.equals(provider) && mds.servicename.equals(servicename)) {
                    return mds;
                }
            }
            return null;
        }
}
