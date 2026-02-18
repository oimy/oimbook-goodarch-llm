package oimbook.goodarch.domain.utils.log

import org.slf4j.Logger

class SimpleLogger(private val logger: Logger) {

    fun debug(callMessage: () -> Any?) {
        if (logger.isDebugEnabled) {
            logger.debug(callMessage().toString())
        }
    }

    fun info(callMessage: () -> Any?) {
        if (logger.isInfoEnabled) {
            logger.info(callMessage().toString())
        }
    }

    fun warn(callMessage: () -> Any?) {
        if (logger.isWarnEnabled) {
            logger.warn(callMessage().toString())
        }
    }

    fun error(callMessage: () -> Any?) {
        logger.error(callMessage().toString())
    }

}