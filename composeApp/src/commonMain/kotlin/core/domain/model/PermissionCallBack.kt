package core.domain.model

data class PermissionCallBack(
    val onGranted: () -> Unit = {},
    val onDenied: () -> Unit = {}
)
