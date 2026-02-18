package oimbook.goodarch.domain.utils.log

import org.slf4j.LoggerFactory

abstract class Logging {

    val log: SimpleLogger = SimpleLogger(logger = LoggerFactory.getLogger(javaClass.enclosingClass))

}