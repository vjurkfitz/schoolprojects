""" Sort Demonstrator/Sort Logic
    Author: Victoria Jurkfitz Kessler Thibes
    Date: Dec. 02, 2016
    Course: CST8333 - Programming Language Research Project
"""

from structures import LinkedList
from structures import Stack
from structures import BinaryTree
from copy import deepcopy


class Bubblesort:
    """" Implementes the bubble sort algorithm """

    iterations = 0
    # Number of iterations that it took to complete

    sorted_data = []
    # Sorted results

    def __init__(self, data):
        """ Constructor """
        self.sorted_data = data

    def start(self):
        """ Calls sort for the first time """
        if len(self.sorted_data) > 0:
            data = deepcopy(self.sorted_data)

            for i in range(0, len(data) - 1):
                swapped = False

                for j in range(0,len(data)-1):
                    if data[j] > data[j+1]:
                        aux = data[j]
                        data[j] = data[j+1]
                        data[j+1] = aux
                        swapped = True

                self.iterations += 1

                if not swapped:
                    break

            self.sorted_data = deepcopy(data)

    def worst_case(self):
        """ Prepares worst case scenario: smallest at the end """

        smallest = self.sorted_data[0]
        index = 0

        for i in range(0,len(self.sorted_data)-1):
            if self.sorted_data[i] < smallest:
                smallest = self.sorted_data[i]
                index = i

        aux = self.sorted_data[len(self.sorted_data)-1]
        self.sorted_data[len(self.sorted_data) - 1] = smallest
        self.sorted_data[index] = aux

    def best_case(self):
        """ Prepares best case scenario: already sorted """
        self.start()
        self.iterations = 0


class Quicksort:
    """" Implements the Quick Sort algorithm """

    iterations = 0
    # Number of iterations that it took to complete

    sorted_data = []
    # Sorted results

    def __init__(self,data):
        """" Constructor """
        self.sorted_data = data

    def start(self):
        """ Calls the algorithm for the first time """
        if len(self.sorted_data) > 0:
            self.sort(0,len(self.sorted_data)-1)

    def sort(self,ini,fini):
        """ Partition divides array in 2
            Then sort is called for each part """

        if ini < fini:
            q = self.partition(ini,fini)
            self.iterations += 1
            self.sort(ini,q-1)
            self.sort(q+1,fini)


    def partition(self,ini,fini):
        """ Organizes two parts of an array
            according to a pivot value
            Everything greater: to the right
            Everything smaller: to the left """
        x = self.sorted_data[fini]
        i = ini

        for j in range(ini,fini):
            if self.sorted_data[j] < x:
                aux = self.sorted_data[j]
                self.sorted_data[j] = self.sorted_data[i]
                self.sorted_data[i] = aux
                i += 1

        aux = self.sorted_data[i]
        self.sorted_data[i] = self.sorted_data[fini]
        self.sorted_data[fini] = aux

        return i

    def worst_case(self):
        """ Prepares worst case: already sorted """
        self.start()
        self.iterations = 0


class Mergesort:
    """ Implementes the Merge Sort algorithm """

    sorted_data = []
    # Final results

    iterations = 0
    # Number of iterations that it took to resolve

    def __init__(self,data):
        """" Constructor """
        self.sorted_data = data

    def start(self):
        """ Calls method for the first time """
        if len(self.sorted_data) > 0:
            data = self.sorted_data
            self.sorted_data = self.divide(data)

    def divide(self,data):
        """ Divides the array into minimal parts """
        if len(data) == 1:
            return data

        else:
            l1 = []
            l2 = []
            for i in range(0,int(len(data)/2)):
                l1.append(data[i])

            for i in range(int(len(data)/2),len(data)):
                l2.append(data[i])

            l1 = self.divide(l1)
            l2 = self.divide(l2)
            self.iterations += 1

            return self.merge(l1,l2)


    def merge(self,l1,l2):
        """ Merges the divided arrays into a big sorted one
            Sorts them as they are merged together """
        merged = []

        while len(l1) > 0 and len(l2) > 0:
            if l1[0] > l2[0]:
                merged.append(l2.pop(0))

            else:
                merged.append(l1.pop(0))

        while len(l1) > 0:
            merged.append(l1.pop(0))

        while len(l2) > 0:
            merged.append((l2.pop(0)))

        return merged


class SortLinkedList:
    """ Merge sort of a linked list """

    sorted_data = []
    # Final results

    iterations = 0
    # Number of iterations that it took to resolve

    def __init__(self,data):
        """" Constructor """
        if data.node_data != '':
            self.sorted_data = self.divide(data)

    def divide(self,data):

        if data.next_node == '':
            return data

        else:

            aux = deepcopy(data)
            list_len = 1

            # Count list length by running through nodes
            while aux.next_node != '':
                list_len += 1
                aux = aux.next_node

            # Create two lists
            lower = data

            if list_len > 2:
                for i in range(0,int(list_len/2)):
                    lower = lower.next_node

            # Second half of the list
            higher = lower.next_node

            # Take out link to next half
            lower.next_node = ''
            lower = data

            lower = self.divide(lower)
            higher = self.divide(higher)
            self.iterations += 1

            return self.merge(deepcopy(lower), deepcopy(higher))

    def merge(self,l1,l2):

        l1_len = 1
        l2_len = 1
        merged = LinkedList([''])
        curr = LinkedList([''])

        # Count L1 length
        aux = deepcopy(l1)
        while aux.next_node != '':
            l1_len += 1
            aux = aux.next_node

        # Count L2 length
        aux = deepcopy(l2)
        while aux.next_node != '':
            l2_len += 1
            aux = aux.next_node

        while l1_len > 0 and l2_len > 0:
            if l1.node_data > l2.node_data:
                if merged.node_data == '':
                    merged.node_data = l1.node_data
                    curr = merged

                else:
                    curr.next_node = LinkedList([l1.node_data])
                    curr = curr.next_node

                if l1_len > 1:
                    l1 = l1.next_node

                l1_len -= 1

            else:
                if merged.node_data == '':
                    merged.node_data = l2.node_data
                    curr = merged

                else:
                    curr.next_node = LinkedList([l2.node_data])
                    curr = curr.next_node

                if l2_len > 1:
                    l2 = l2.next_node

                l2_len -= 1

        if l1_len > 0:
            if merged.node_data == '':
                merged = l1

            else:
                curr.next_node = l1

        if l2_len > 0:

            if merged.node_data == '':
                merged = l2

            else:
                curr.next_node = l2

        return merged


class TreeSort:

    items = []
    iterations = 0
    sorted_tree = []

    def __init__(self,data):
        """" Constructor """
        self.items = deepcopy(data)

    def start(self):

        if len(self.items) > 0:
            self.sorted_tree = BinaryTree([''],'')

            for item in self.items:
                self.insert(item,self.sorted_tree)

            self.check_order(self.sorted_tree)

    def insert(self,item,tree):

        if tree.node_data == '':
            tree.node_data = item
            tree.left = BinaryTree([''],tree)
            tree.right = BinaryTree([''],tree)

        else:
            if item < tree.node_data:
                self.insert(item,tree.left)
            else:
                self.insert(item,tree.right)

    def heapify(self,tree):

        if tree.parent != '':
            if tree.node_data > tree.parent.node_data:
                aux = tree.parent.node_data
                tree.parent.node_data = tree.node_data
                tree.node_data = aux
                self.heapify(tree.parent)

    def check_order(self,tree):

        if tree.node_data == '':
            return

        else:
            self.check_order(tree.left)
            self.heapify(tree)
            self.check_order(tree.right)
            self.iterations += 1


class SortStack:

    sorted_stack = []
    iterations = 0

    def __init__(self,data):
        """ Constructor """

        if len(data) > 0:
            self.sorted_stack = Stack(deepcopy(data))

            sorted = self.sorted_stack
            helper = Stack([''])

            while sorted.node_data != '':
                top = sorted.pop()
                while helper.node_data != '' and helper.node_data < top:
                    sorted.push(helper.pop())
                helper.push(top)
                self.iterations += 1

            while helper.node_data != '':
                sorted.push(helper.pop())



