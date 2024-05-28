package org.abapps.app.resource.strings.arabic

import org.abapps.app.resource.strings.IStringResources

data class Arabic(
    override val invoice: String = "فاتورة",
    override val transfer: String = "نقل",
    override val settings: String = "الاعدادات",
    override val exit: String = "الخروج",
    override val userName: String = "اسم المستخدم",
    override val password: String = "كلمة المرور",
    override val ok: String = "تم",
    override val cancel: String = "الغاء",
    override val tryAgain: String = "حاول مجددا",
    override val somethingWrongHappened: String = "يبدو أن حصل خطأ ما الرجاء المحاولة مره اخري",
    override val noInternetMessage: String = "لا يوجد انترنت",
    override val noInternetDescription: String = "بالرجاء التأكد من الاتصال بالانترنت و حاول مره اخري",
    override val nullDataMessage: String = "لا يوجد",
    override val nullDataDescription: String = "لا يوجد بيانات قم باضافة بيانات ثم حاول مره اخري",
    override val enterYourInfo: String = "ادخل بياناتك",
    override val warning: String = "تحذير",
    override val error: String = "خطأ",
    override val userNotFound: String = "هذا المستخدم غير موجود",
    override val enterYourPasscode: String = "ادخل الرقم السري",
    override val retry: String = "اعادة المحاوله",
    override val doYouWantToCloseApp: String = "هل تريد اغلاق التطبيق ؟",
    override val no: String = "لا",
    override val yes: String = "نعم",
) : IStringResources
