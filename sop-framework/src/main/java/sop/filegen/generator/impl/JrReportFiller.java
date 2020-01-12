package sop.filegen.generator.impl;

import java.sql.Connection;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * @Author: LCF
 * @Date: 2020/1/8 17:55
 * @Package: sop.filegen.generator.impl
 */


public class JrReportFiller {

    private static final Logger logger = LoggerFactory.getLogger(JrReportFiller.class);

    private DataSource jdbcDataSource;


    public JrReportFiller(DataSource jdbcDataSource) {
        super();
        this.jdbcDataSource = jdbcDataSource;
    }

    public JasperPrint fillReport(String mainReport, Map<String, Object> model) throws Exception {
        // Determine main report.zz
        if (mainReport == null) {
            throw new IllegalStateException("No main report defined for 'fillReport' - " +
                    "specify a 'url' on this view or override 'getReport()' or 'fillReport(Map)'");
        }

        return doFillReport(mainReport, model, jdbcDataSource);

    }


    public JasperPrint fillReportWithJavaBean(String mainReport, Map<String, Object> model) throws Exception {
        // Determine main report.zz
        if (mainReport == null) {
            throw new IllegalStateException("No main report defined for 'fillReport' - " +
                    "specify a 'url' on this view or override 'getReport()' or 'fillReport(Map)'");
        }

        return doFillReport(mainReport, model, null);

    }

    /**
     * Fill the given report using the given JDBC DataSource and model.
     */
    private JasperPrint doFillReport(String report, Map<String, Object> model, DataSource ds) throws Exception {


        // Use the JDBC DataSource.
        if (logger.isDebugEnabled()) {
            logger.debug("Filling report using JDBC DataSource [" + ds + "]");
        }
        Connection con = ds.getConnection();
        try {
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            return JasperFillManager.fillReport(report, model, con);
        } finally {
            try {
                con.close();
            } catch (Throwable ex) {
                logger.debug("Could not close JDBC Connection", ex);
            } finally {
                if (logger.isDebugEnabled()) {
                    logger.debug("Filling report completed.");
                }
            }
        }
    }

}
