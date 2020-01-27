package dwz.framework.identity;

/**
 * @Author: LCF
 * @Date: 2020/1/8 16:42
 * @Package: dwz.framework.identity
 */

/**
 * @pdOid 218a6632-d2ca-4255-b73e-643eba641586
 */
public interface IdentityProvider {

    /**
     * @param subject
     * @pdOid 26ae022d-15ac-4415-be04-d8d86c652184
     */
    public Identity createIdentity(String identityString);
}
