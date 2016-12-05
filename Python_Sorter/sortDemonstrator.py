""" Sort Demonstrator: Final version
    Author: Victoria Jurkfitz Kessler Thibes
    Date: Dec. 02, 2016
    Course: CST8333 - Programming Language Research Project
"""

import tkinter as tk
import tkinter.filedialog as filedialog
import threading
import sortLogic as sorters
from structures import LinkedList
from structures import BinaryTree
from structures import Stack

from copy import deepcopy
from random import randint


class SortThread(threading.Thread):
    """ Runs a sorting algorithm """

    sorter = ""
    # Sorter class (BubbleSort, QuickSort or MergeSort)

    def __init__(self,sorter):
        """ Constructor """
        threading.Thread.__init__(self)
        self.sorter = sorter

    def run(self):
        """ Overwrites Thread.run() """
        self.sorter.start()


class MainWindow(tk.Frame):
    """Main window"""

    _data = []
    # Data that will be sorted

    def __init__(self, parent):
        """Constructor"""

        tk.Frame.__init__(self, parent)
        self.parent = parent
        self.initialize()

    def initialize(self):
        """Initialize widgets"""

        self.grid()
        self.parent.title("Sort Demonstrator")

        # Configure columns
        for i in range(2):
            self.columnconfigure(i, pad=25)

        # Configure rows
        for i in range(7):
            self.rowconfigure(i, pad=15)

        # Widgets
        load_button = tk.Button(self, text="Load data set from file",
                                command=self.load_from_file, width=25)
        load_button.grid(column=0, row=0)

        create_button = tk.Button(self, text="Create new data set",
                                  command=self.create_data, width=25)
        create_button.grid(column=1, row=0)

        pick_struct_lbl = tk.Label(self, text='Pick a data structure')
        pick_struct_lbl.grid(column=0, row=2)

        struct_list = tk.Listbox(self, width=30, height=6, selectmode=tk.SINGLE)
        struct_list.grid(column=0, row=3, rowspan=3)

        for item in ["Array", "Linked List", "Binary Tree",
                     "Stack"]:
            struct_list.insert(tk.END, item)

        struct_list.select_set(0)

        check_data_button = tk.Button(self, text=u"Check data set",
                                      command= lambda: self.check_data(str
                                        (struct_list.get(struct_list.curselection()))), width=25)
        check_data_button.grid(column=1, row=2)

        random_button = tk.Button(self, text=u"Randomize data set",
                                  command=self.randomize_data, width=25)
        random_button.grid(column=1, row=3)

        sort_as_is_button = tk.Button(self, text=u"Sort data set",
                                      command= lambda: self.sort(str
                                        (struct_list.get(struct_list.curselection()))),
                                        width=25)
        sort_as_is_button.grid(column=1, row=4)

        self.pack()

    def load_from_file(self):
        """ Opens new window to read a file """
        LoadFileWindow(self)

    def create_data(self):
        """ Creates random set of integers """

        self._data = []
        i = randint(2,20)       # Size of the array - min:2  max: 20

        for j in range (0,i-1):
            self._data += {randint(1,i)}

        print("Random set of integer numbers created")

    def randomize_data(self):
        """ Randomizes _data array
        Does not check for items that have already been swapped """
        for i in range (0,len(self._data)):
            j = randint(0,len(self._data)-1)
            aux = self._data[j]
            self._data[j] = self._data[i]
            self._data[i] = aux

        print("Randomized")

    def sort(self,data_type):
        """ Opens new window comparing sorting methods """
        SortDataWindow(self, self._data,data_type)

    def set_data(self, data):
        """ Setter for data """
        self._data = data

    def check_data(self,data_type):
        """ Opens new window to show dataset """
        CheckDataWindow(self._data, data_type, self)


class LoadFileWindow(tk.Toplevel):
    """ Window where user can select a file to be loaded """

    # Data read from file
    data = []

    # Text that is displayed in the textfield
    entry_variable = ''

    # Textfield
    entry = ''

    def __init__(self, parent):
        """ Constructor """

        tk.Toplevel.__init__(self)
        self.parent = parent
        self.initialize()

    def initialize(self):
        """ Initialize widgets """

        self.grid()
        self.title("Load data set from file")

        # Configure columns
        for i in range(3):
            self.columnconfigure(i, pad=10)

        # Configure rows
        for i in range(2):
            self.rowconfigure(i, pad=10)

        load_file = tk.Label(self, text='Select a file',
                             anchor="w", fg="black")

        load_file.grid(column=0, row=0, columnspan=3, sticky='W')

        self.entry_variable = tk.StringVar()
        self.entry = tk.Entry(self, textvariable=self.entry_variable,
                              width=40)
        self.entry.grid(column=0, row=1)

        select_button = tk.Button(self, text=u"...",
                                  command=self.on_select_click, width=10)
        select_button.grid(column=1, row=1)

        load_button = tk.Button(self, text=u"Load",
                                command=self.on_load_click, width=10)
        load_button.grid(column=2, row=1)

    def on_select_click(self):
        """ Opens a file dialog to select file from system """

        d = filedialog.FileDialog(self)
        self.entry_variable.set(d.go())

    def on_load_click(self):
        """ Gets text from entry and passes it to load_file """

        if self.entry.get() != '':
            self.load_file(self.entry.get())
            self.parent.set_data(self.data)
            print('File loaded.')
            self.withdraw()

        else:
            pass

    def load_file(self,file_path):
        """ Loads file from system into data [] """
        self.data = []

        try:
            file = open(file_path, 'r')
            is_string = False

            for line in file:
                for c in line:
                    if ord(c.upper()) in range(65,90):
                        is_string = True
                        break;

                if is_string:
                    self.data += {line.replace('\n', '')}

                else:
                    self.data += {int(line.replace('\n',''))}

            file.close()

        except IOError:
            self.entry_variable.set(self.entry_variable.get()
                                    + ": Invalid path")


class CheckDataWindow(tk.Toplevel):
    """ Window where user checks the data that has been loaded """

    def __init__(self, data, data_type, parent):
        """ Constructor """

        tk.Toplevel.__init__(self)
        self.parent = parent
        data_copy = data[:]

        if data_type == "Stack":
            new_data = Stack(data_copy)
        elif data_type == "Binary Tree":
            new_data = BinaryTree(data_copy, '')
        elif data_type == "Linked List":
            new_data = LinkedList(data_copy)
        else:
            new_data = data_copy

        self.initialize(new_data)

    def initialize(self, data):
        """ Initialize widgets """

        self.grid()
        self.title("Check dataset")

        # Configure columns
        for i in range(4):
            self.columnconfigure(i, pad=10)

        # Configure rows
        for i in range(2):
            self.rowconfigure(i, pad=10)

        data_loaded_label = tk.Label(self, text='Current dataset:',
                             anchor="w", fg="black")

        data_loaded_label.grid(column=0, row=0, sticky='W')

        data_text = ""

        try:
            # Array
            for item in data:
                data_text += str(item) + "\n"

            if data_text == "":
                data_text = "Empty dataset"

        except:
            # Not Array
            #try:
            data_text = data.get_string()

            #except:
            #    data_text = "Empty dataset"

        data_print_label = tk.Label(self, text=data_text, anchor="w",
                                    fg="black")
        data_print_label.grid(column=0, row=1, sticky='WE')


class SortDataWindow(tk.Toplevel):
    """ Window showing the different sorting methods,
        the resulted data and how many iteractions it took """

    def __init__(self, parent, data, data_type):
        """ Constructor """

        tk.Toplevel.__init__(self)
        self.parent = parent

        self.grid()
        self.title("Results")

        for i in range(3):
            self.columnconfigure(i, pad=10)

        # Configure rows
        for i in range(5):
            self.rowconfigure(i, pad=10)

        results_title = tk.Label(self, text="All results")
        results_title.grid(column=0,row=0,columnspan=2)

        if data_type == 'Array':
            # Bubble sort
            try:
                bubble = sorters.Bubblesort(data[:])

                # Dataset as is
                thread1 = SortThread(bubble)
                thread1.start()

                # Worst case scenario
                bubble_worst = deepcopy(bubble)
                bubble_worst.worst_case()

                thread2 = SortThread(bubble_worst)
                thread2.start()

                # Best case scenario
                bubble_best = deepcopy(bubble)
                bubble_best.best_case()

                thread3 = SortThread(bubble_best)
                thread3.start()

                results_bubble = tk.Label(self, text='BUBBLE SORT\nIterations:\nDataset as is: ' + str(bubble.iterations) + '\nBest case: ' + str(bubble_best.iterations) + '\nWorst case: ' + str(bubble_worst.iterations))
                results_bubble.grid(column=0,row=1)

                bubble_list = ""

                for item in bubble.sorted_data:
                    bubble_list += str(item) + '\n'

                print_bubble_list = tk.Label(self,text=bubble_list)
                print_bubble_list.grid(column=0,row=2)

            except: print("Error - Bubble sort")

            # Quick sort
            try:
                quickie = sorters.Quicksort(data[:])

                SortThread(quickie).start()

                results_quick = tk.Label(self,text='Quick sort:\nIterations: ' + quickie.iterations.__str__())
                results_quick.grid(column=1,row=1)

                quick_list = ""

                for item in quickie.sorted_data:
                    quick_list += str(item) + '\n'

                print_quick_list = tk.Label(self, text=quick_list)
                print_quick_list.grid(column=1, row=2)

            except: print("Error - Quick sort")

            # Merge sort
            try:
                merge = sorters.Mergesort(data[:])

                SortThread(merge).start()

                results_merge = tk.Label(self,text="Merge sort:\nIterations: " + merge.iterations.__str__())
                results_merge.grid(column=3,row=1)

                merge_list = ""

                for item in merge.sorted_data:
                    merge_list += str(item) + '\n'

                print_merge_list = tk.Label(self, text=merge_list)
                print_merge_list.grid(column=3, row=2)

            except: print("Error - Merge sort")

        elif data_type == 'Linked List':
            l_list = LinkedList(data)

            linked = sorters.SortLinkedList(l_list)

            results_linked = tk.Label(self, text="Linked list sort:\nIterations: " + str(linked.iterations))
            results_linked.grid(column=0, row=1)

            try: print_linked = tk.Label(self, text=linked.sorted_data.get_string())
            except: print_linked = tk.Label(self, text="")

            print_linked.grid(column=0,row=2)

        elif data_type == 'Binary Tree':

            tree_sort = sorters.TreeSort(data)
            tree_sort.start()

            results_tree = tk.Label(self,text="Tree sort:\nIterations: " + str(tree_sort.iterations))
            results_tree.grid(column=0, row=1)

            try: print_tree = tk.Label(self,text=tree_sort.sorted_tree.get_string())
            except: print_tree = tk.Label(self,text="")

            print_tree.grid(column=0, row=2)

        elif data_type == 'Stack':

            stack_sort = sorters.SortStack(data)

            results_stack = tk.Label(self,text="Sort stack:\nIterations: " + str(stack_sort.iterations))
            results_stack.grid(column=0, row=1)

            try: print_stack = tk.Label(self,text=stack_sort.sorted_stack.get_string())
            except: print_stack = tk.Label(self,text="")

            print_stack.grid(column=0, row=2)

        # Only implemented for array yet
        else:
            tbd_lbl = tk.Label(self,text="Sort for other structures (not Array)\nis not implemented yet")
            tbd_lbl.grid(column=0,row=1)

# Main
if __name__ == '__main__':

    root = tk.Tk()
    app = MainWindow(root)
    app.mainloop()

