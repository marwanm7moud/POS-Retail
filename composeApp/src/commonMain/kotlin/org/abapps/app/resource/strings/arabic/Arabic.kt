package org.abapps.app.resource.strings.arabic

import org.abapps.app.resource.strings.IStringResources

data class Arabic(
    override val click: String = "اضغط",
    override val logo: String = "شعار",
    override val login: String = "تسجيل الدخول",
    override val logout: String = "تسجيل الخروج",
    override val admin: String = "الادارة",
    override val exit: String = "الخروج",
    override val dinningIn: String = "الصالة",
    override val outlet: String = "الفرع",
    override val settings: String = "الاعدادت",
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
    override val passcode: String = "الرقم السري",
    override val userNameEmptyException: String = "يجب ادخال الاسم",
    override val passwordEmptyException: String = "يجب ادخال كلمة المرور",
    override val passcodeEmptyException: String = "يجب ادخال كلمة السر",
    override val authEmptyException: String = "يجب ادخال اسمك مع كلمة المرور",
    override val doYouWantToCloseApp: String = "هل تريد غلق التطبيق",
    override val covers: String = "عدد الاشخاص",
    override val welcome: String = "مرحبا",
    override val bye: String = "سلام",
    override val itemAddedSuccess: String = "تم اضافة المنتج بنجاح",
    override val alreadyOpenChecks: String = "في شيك مفتوح بالفعل عايز واحد جديد ؟",
    override val comment: String = "تعليق",
    override val createTableGuest: String = "فتح طرابيزه مخصوص",
    override val showAllTableGuest: String = "عرض كل الطرابيزات المخصوصة",
    override val tableName: String = "اسم الطرابيزة",
    override val doYouWantToAbortCheck: String = "هل تريد غلق الشيك",
    override val itemAlreadyExist: String = "هذا المنتج موجود، هل تريد إضافة كمية أخرى؟",
    override val item: String = "منتج",
    override val quantity: String = "كميه",
    override val price: String = "سعر",
    override val totalItems: String = "اجمالي المنتجات",
    override val totalCheckPrice: String = "اجمالي سعر المنتجات علي الشيك",
    override val fire: String = "اجهز",
    override val enterComment: String = "اكتب تعليق",
    override val checkNumber: String = "رقم الشيك",
    override val no: String = "لا",
    override val yes: String = "نعم",
    override val enterPrice: String = "ادخل السعر",
    override val openPrice: String = "السعر المفتوح",
) : IStringResources
