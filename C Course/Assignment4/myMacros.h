#pragma once
#ifndef __MY_MACROS__
#define __MY_MACROS__

#define CHECK_RETURN_0(pointer) {\
	if(pointer == NULL){\
		return 0;\
	}\
}


#define CHECK_RETURN_NULL(pointer) {\
	if(pointer == NULL){\
		return NULL;\
	}\
}
#define CHECK_MSG_RETURN_0(pointer, msg) {\
	if(pointer == NULL){\
		puts(msg);\
		return 0;\
	}\
}

#define MSG_CLOSE_RETURN_0(file, msg) {\
	puts(msg); \
	fclose(file);\
	return 0; \
}

#define CHECK_0_MSG_COLSE_FILE(file, msg, value){\
	if (value == 0) {\
		MSG_CLOSE_RETURN_0(file, msg);\
	}\
}

#define CHECK_NULL__MSG_COLSE_FILE(file, msg, value) {\
	if (value == NULL) {\
		MSG_CLOSE_RETURN_0(file, msg);\
	}\
}



#endif // __MY_MACROS__
