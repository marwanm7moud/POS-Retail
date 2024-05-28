package org.abapps.app.resource.strings.english

import org.abapps.app.resource.strings.IStringResources

data class English(
    override val invoice: String = "Invoice",
    override val transfer: String = "Transfer",
    override val settings: String = "Settings",
    override val exit: String = "Exit",
) : IStringResources