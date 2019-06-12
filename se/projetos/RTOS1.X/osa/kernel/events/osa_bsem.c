/*
 ************************************************************************************************

 *  File:           osa_bsem.c
 *
 *  Programmer:     Timofeev Victor
 ************************************************************************************************
 */


#if OS_BSEMS > 0

    #if (OS_BANK_BSEM == 0) && defined(__OSA_PIC18_MPLABC__)
    #pragma udata access os_bsems
    #endif

    volatile OS_BSEM_BANK    OST_WORD   _OS_Bsems[(OS_BSEMS + _OST_INT_SIZE-1) / _OST_INT_SIZE] ;

    #if (OS_BANK_BSEM == 0) && defined(__OSA_PIC18_MPLABC__)
    #pragma udata
    #endif

#endif




//******************************************************************************
//  END OF FILE osa_bsem.c
//******************************************************************************

