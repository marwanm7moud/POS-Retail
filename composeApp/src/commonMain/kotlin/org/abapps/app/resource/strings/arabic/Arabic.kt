package org.abapps.app.resource.strings.arabic

import org.abapps.app.resource.strings.IStringResources

data class Arabic(
    override val invoice: String = "فاتورة",
    override val transfer: String = "نقل",
    override val settings: String = "الاعدادات",
    override val exit: String = "الخروج",
) : IStringResources
