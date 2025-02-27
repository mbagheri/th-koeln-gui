//Mahdi Bagheri

#include <windows.h>
#include <iostream>

#define FILE_MENU_EXIT 1
#define FILE_READ_MENU 2
#define OPEN_FILE_BUTTON 3

//Initialisierung globaler Variablen
HWND hEdit, hwnd;
const char className[] = "myWindowClass";
char window_title [] = "";

//Initialisierung der Methoden
LRESULT CALLBACK WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam);
void AddMenus(HWND hwnd);
void AddControls(HWND hwnd);
void open_file(HWND hwnd);
void display_file(char* path);

//WINAPI Main Methode
int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance,
    LPSTR lpCmdLine, int nCmdShow)
{
    WNDCLASSEX wc;
    MSG Msg;

    //Schritt 1: Registrierung der Windowclass
    wc.cbSize        = sizeof(WNDCLASSEX);
    wc.style         = 0;
    wc.lpfnWndProc   = WndProc;
    wc.cbClsExtra    = 0;
    wc.cbWndExtra    = 0;
    wc.hInstance     = hInstance;
    wc.hIcon         = LoadIcon(NULL, IDI_APPLICATION);
    wc.hCursor       = LoadCursor(NULL, IDC_ARROW);
    wc.hbrBackground = (HBRUSH)(COLOR_WINDOW+1);
    wc.lpszMenuName  = NULL;
    wc.lpszClassName = className;
    wc.hIconSm       = LoadIcon(NULL, IDI_APPLICATION);

    if(!RegisterClassEx(&wc))
    {
        MessageBox(NULL, "Window Registration Failed!", "Error!",
            MB_ICONEXCLAMATION | MB_OK);
        return 0;
    }

    //Schritt 2: Erstellen des Fensters
    hwnd = CreateWindowEx(
        WS_EX_CLIENTEDGE,
        className,
        "Some Title",
        WS_OVERLAPPEDWINDOW,
        CW_USEDEFAULT, CW_USEDEFAULT, 800, 500,
        NULL, NULL, hInstance, NULL);

    if(hwnd == NULL)
    {
        MessageBox(NULL, "Window Creation Failed!", "Error!",
            MB_ICONEXCLAMATION | MB_OK);
        return 0;
    }

    ShowWindow(hwnd, nCmdShow);
    UpdateWindow(hwnd);

    //Schritt 3: Message Loop
    while(GetMessage(&Msg, NULL, 0, 0) > 0)
    {
        TranslateMessage(&Msg);
        DispatchMessage(&Msg);
    }
    return Msg.wParam;
}

//Schritt 4: Die Window Procedure, in der die anderen Methoden aufgerufen werden
// nach einem eintreffenden Event vom Message Loop wahrgenommen werden.
LRESULT CALLBACK WndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    switch(msg)
    {
        case WM_COMMAND:
        {
            switch(wParam){
            case FILE_MENU_EXIT:
                DestroyWindow(hwnd);
                break;
            case FILE_READ_MENU:
                open_file(hwnd);
                break;
            case OPEN_FILE_BUTTON:
                open_file(hwnd);
                break;
            }
        }
        break;
        case WM_CREATE:
            AddMenus(hwnd);
            AddControls(hwnd);
            break;
        case WM_CLOSE:
            DestroyWindow(hwnd);
        break;
        case WM_DESTROY:
            PostQuitMessage(0);
        break;
        default:
            return DefWindowProc(hwnd, msg, wParam, lParam);
    }
    return 0;
}

//Methode, welche zum Anzeigen der Menue� Elemente dient
void AddMenus(HWND hwnd){
    HMENU hMenu = CreateMenu();
    HMENU hFileExitMenu = CreateMenu();
    HMENU hFileReadMenu = CreateMenu();

    AppendMenu(hFileExitMenu, MF_STRING, FILE_MENU_EXIT, "Beenden" );
    AppendMenu(hMenu, MF_POPUP, (UINT_PTR)hFileExitMenu, "Datei");
    AppendMenu(hMenu, MF_STRING, FILE_READ_MENU, "Text einlesen");

    SetMenu(hwnd, hMenu);
}

//Methode, welche die ein Textfeld zum Anzeigen der Textinhalte darstellt und
//einen Button, der zum Beenden des Programms dienen soll
void AddControls(HWND hwnd){
    hEdit = CreateWindowW(L"Edit", L"", WS_VISIBLE | WS_CHILD | ES_MULTILINE | WS_BORDER | ES_AUTOVSCROLL, 0, 0, 800, 400, hwnd, NULL, NULL, NULL);
    CreateWindowW(L"Button", L"Beenden", WS_CHILD | WS_VISIBLE , 350, 400, 100, 35, hwnd, (HMENU)FILE_MENU_EXIT, NULL, NULL );
}

//Methode, welche ein Dialog oeffnet, damit man eine Datei ausw�hlen kann,
//dessen Inhalt dann angezeigt wird im Fenster
void open_file(HWND hwnd){
    OPENFILENAME ofn;
    ZeroMemory(&ofn, sizeof(OPENFILENAME));

    char file_name[100];

    ofn.lStructSize = sizeof(OPENFILENAME);
    ofn.hwndOwner = hwnd;
    ofn.lpstrFile = file_name;
    ofn.lpstrFile[0] = '\0';
    ofn.nMaxFile = 100;
    ofn.lpstrFilter = "Text Files\0*.TXT\0ASC Files\0*.ASC\0";
    ofn.nFilterIndex = 1;

    if(GetOpenFileName(&ofn)){
        display_file(ofn.lpstrFile);
    }
}


//Methode, welche einen Pfad erh�lt und mit Hilfe dessen eine Datei ausfindig macht
//und den Inhalt ausliest und im Fenster ausgibt.
//Au�erdem wird gleichzeitig mit Hilfe des Pfades der Dateiname ermittelt und als neuen Fenster Titel �bernommen
void display_file(char* path){
    FILE *file;
    file = fopen(path, "rb");
    fseek(file, 0, SEEK_END);
    int _size = ftell(file);
    rewind(file);
    char *data = new char(_size+1);
    fread(data, _size, 1, file);
    data[_size] = '\0';

    std::string file_as_string;
    file_as_string += path;
    std::string base_filename = file_as_string.substr(file_as_string.find_last_of("/\\")+1);

    strcpy(window_title, base_filename.c_str());
    SetWindowText(hwnd, window_title);
    SetWindowText(hEdit, data);
}

